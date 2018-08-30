(ns navdist-console.events
  (:require
   [cljs.reader :as reader]
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [navdist-console.db :as db]
   [taoensso.timbre :as timbre]))

;; db store preparations
;; initializing db
(defn-traced initialize-db
  [cofx _]
  (let [home-dir (:os-home-dir cofx)
        db (:db cofx)]
    (timbre/spy
    (if (nil? home-dir)
      {:db db/default-db}
      {:db (assoc-in db/default-db [:config :screenshot :path] (str home-dir "/Downloads/"))}))))

(re-frame/reg-event-fx
 :initialize-db
 [(re-frame/inject-cofx :os-home-dir)]
 initialize-db)

;; read persisted db from file
(defn-traced update-db-persist
  [cofx _]
  (timbre/spy cofx)
  {:read-edn {:user-data-dir (:user-data-dir cofx)}})

(re-frame/reg-event-fx
 :update-db-persist
 [(re-frame/inject-cofx :user-data-dir)]
 update-db-persist)

;; updateing
(defn-traced initialize-last
  [cofx [_ v]]
  (timbre/spy v)
  (let [db (:db cofx)
        update-db (-> (:file-db v)
                      str
                      reader/read-string
                      (as-> x (update-in db [:config] conj x))
                      timbre/spy)]
    {:db update-db
     :resize-electron-window {:current-window (:current-app cofx)
                              :zoom-factor (get-in update-db [:config :zoom-factor])}}))

(re-frame/reg-event-fx
 :initialize-last
 [(re-frame/inject-cofx :current-app)]
 initialize-last)

;; webview preparations
;; initialize webview event to inject css to show only game screen in webview
(defn-traced initialize-webview
  [cofx _]
  (let [db (:db cofx)]
    {:webview-injectcss {:target-webview (:main-webview cofx)
                         :user-css (get-in db [:config :user-css])}}))

(re-frame/reg-event-fx
 :initialize-webview
 [(re-frame/inject-cofx :main-webview)]
 initialize-webview)

;; close app
(defn-traced close-app
  [cofx v]
  {:shutdown-app {:current-app (:current-app cofx)}})

(re-frame/reg-event-fx
 :close-app
 [(re-frame/inject-cofx :current-app)]
 close-app)

;; effecter
;; take screenshot of webview
(defn-traced take-screenshot
 [cofx _]
  (let [db (:db cofx)
        ss-config (get-in db [:config :screenshot])
        wv (:main-webview cofx)
        ss-time (:now cofx)]
    {:webview-screenshot
     {:config ss-config
      :target-webview wv
      :time ss-time
      :on-success [:notify-success]
      :on-failure [:notify-failure]}}))

(re-frame/reg-event-fx
 :take-screenshot
 [(re-frame/inject-cofx :main-webview)
  (re-frame/inject-cofx :now)]
 take-screenshot)

;; toggle volume on/mute of webview
(defn-traced toggle-volume-state
  [cofx _]
  (let [volume (get-in cofx [:db :state :volume])
        wv (:main-webview cofx)]
    {:toggle-volume {:volume (not volume) :target-webview wv}
     :db (assoc-in (:db cofx) [:state :volume] (not volume))}))

(re-frame/reg-event-fx
 :toggle-volume-state
 [(re-frame/inject-cofx :main-webview)]
 toggle-volume-state)

;; do reload of webview
(defn-traced do-reload
  [cofx _]
  (let [db (:db cofx)
        reload-state (get-in db [:state :app-bar :reload-enabled])
        wv (:main-webview cofx)]
    {:webview-reload {:target-webview wv}
     :db (assoc-in db [:state :app-bar :reload-enabled] (not reload-state))}))

(re-frame/reg-event-fx
 :do-reload
 [(re-frame/inject-cofx :main-webview)]
 do-reload)

;; state
;; open directory dialog to change screenshot saving directory
(defn-traced open-screenshot-path-dialog
  [cofx _]
  {:screenshot-path-dialog {:current-dir (get-in cofx [:db :config :screenshot :path])}})

(re-frame/reg-event-fx
 :open-screenshot-path-dialog
 open-screenshot-path-dialog)

;; toggle app menu open/close state
(defn-traced toggle-app-menu
  [db [_ open?]]
  (-> db
      (update-in [:state :app-menu] assoc :open open?)
      (timbre/spy)))

(re-frame/reg-event-db
 :toggle-app-menu
 toggle-app-menu)

;; toggle shutdown dialog open/close state
(defn-traced toggle-dialog-shutdown
  [db [_ open?]]
  (-> db
      (update-in [:state :dialog] assoc :shutdown open?)
      (timbre/spy)))

(re-frame/reg-event-db
 :toggle-dialog-shutdown
 toggle-dialog-shutdown)

(re-frame/reg-event-db
 :toggle-app-bar-reload
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:state :app-bar] {:reload-enabled v})
      (timbre/spy))))

;; success notification
(re-frame/reg-event-db
 :notify-success
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:state :notification] {:open true
                                        :type :normal
                                        :duration 2000
                                        :message (:msg v)})
      (timbre/spy))))

;; notify failure
(re-frame/reg-event-db
 :notify-failure
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:state :notification] {:open true
                                        :type :error
                                        :duration 2000
                                        :message (:msg v)})
      (timbre/spy))))

;; close notification snackbar
(re-frame/reg-event-db
 :close-notification
 (fn-traced
  [db _]
  (-> db
      (assoc-in [:state :notification] {:open false
                                        :type :normal
                                        :duration 1000
                                        :message ""})
      (timbre/spy))))

;; toggle settings dialog open/close state
(re-frame/reg-event-fx
 :toggle-settings
 [(re-frame/inject-cofx :user-data-dir)]
 (fn-traced
  [cofx [_ v]]
  (let [db (:db cofx)]
    (timbre/spy v)
    (if v
      {:db (assoc-in db [:state :settings :open] v)}
      {:db (assoc-in db [:state :settings :open] v)
       :write-edn {:config (:config db)
                   :user-data-dir (:user-data-dir cofx)}}))))

(re-frame/reg-event-db
 :settings-locale
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:config :locale] (keyword v))
      (timbre/spy))))

(re-frame/reg-event-db
 :settings-screenshot-path
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:config :screenshot :path] (:path v))
      (timbre/spy))))

;; settings zoom factor
(defn-traced settings-zoom-factor
  [cofx [_ v]]
  (let [db (:db cofx)]
    {:apply-zoom-factor {:target-webview (:main-webview cofx)
                         :zoom-factor v}
     :resize-electron-window {:current-window (:current-app cofx)
                              :zoom-factor v}
     :db (assoc-in db [:config :zoom-factor] v)}))

(re-frame/reg-event-fx
 :settings-zoom-factor
 [(re-frame/inject-cofx :main-webview)
  (re-frame/inject-cofx :current-app)]
 settings-zoom-factor)
