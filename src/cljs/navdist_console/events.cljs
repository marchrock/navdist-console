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
    (timbre/spy cofx)
    (timbre/spy wv)
    (timbre/spy wv-css)
    (.addEventListener wv "did-finish-load"
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

;; toggle app-menu state
(re-frame/reg-event-db
 :toggle-app-menu
 (fn-traced
  [db [_ open?]]
  (-> db
      (update-in [:state :app-menu] assoc :open open?)
      (as-> x (timbre/spy x)))))

(re-frame/reg-event-db
 :toggle-dialog-shutdown
 (fn-traced
  [db [_ open?]]
  (-> db
      (update-in [:state :dialog] assoc :shutdown open?)
      (as-> x (timbre/spy x)))))
