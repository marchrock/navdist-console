(ns navdist-console.events
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [navdist-console.db :as db]
   [taoensso.timbre :as timbre]))

;; initializing db
(re-frame/reg-event-db
 :initialize-db
 (fn-traced
  [_ _]
  db/default-db))

;; initialize webview event to inject css to show only game screen in webview
(re-frame/reg-event-fx
 :initialize-webview
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [wv (:main-webview cofx)
        db (:db cofx)
        wv-css (get-in db [:config :user-css])]
    (.addEventListener wv "dom-ready"
                       (fn [] (.insertCSS wv wv-css))))))

;; close app
(re-frame/reg-event-fx
 :close-app
 [(re-frame/inject-cofx :current-app)]
 (fn-traced
  [cofx _]
  {:shutdown-app {:current-app (:current-app cofx)}}))

;; effecter
(re-frame/reg-event-fx
 :take-screenshot
 [(re-frame/inject-cofx :main-webview)
  (re-frame/inject-cofx :now)]
 (fn-traced
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
      :on-failure [:notify-failure]}})))

(re-frame/reg-event-fx
 :toggle-volume-state
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [volume (get-in cofx [:db :state :volume])
        wv (:main-webview cofx)]
    {:toggle-volume {:volume (not volume) :target-webview wv}
     :db (assoc-in (:db cofx) [:state :volume] (not volume))})))

(re-frame/reg-event-fx
 :do-reload
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [db (:db cofx)
        reload-state (get-in db [:state :app-bar :reload-enabled])
        wv (:main-webview cofx)]
    {:webview-reload {:target-webview wv}
     :db (assoc-in db [:state :app-bar :reload-enabled] (not reload-state))})))

;; toggle app-menu state
;; toggle app menu open/close state
(re-frame/reg-event-db
 :toggle-app-menu
 (fn-traced
  [db [_ open?]]
  (-> db
      (update-in [:state :app-menu] assoc :open open?)
      (timbre/spy))))

;; toggle shutdown dialog open/close state
(re-frame/reg-event-db
 :toggle-dialog-shutdown
 (fn-traced
  [db [_ open?]]
  (-> db
      (update-in [:state :dialog] assoc :shutdown open?)
      (timbre/spy))))

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
(re-frame/reg-event-db
 :toggle-settings
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:state :settings] {:open v})
      (timbre/spy))))

(re-frame/reg-event-db
 :settings-locale
 (fn-traced
  [db [_ v]]
  (-> db
      (assoc-in [:config :locale] (keyword v))
      (timbre/spy))))
