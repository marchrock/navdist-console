(ns navdist-console.main.events
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.db :as db]
   [navdist-console.main.subs :as subs]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre :refer-macros [spy info]]
   [cljs-time.core :as time]
   [cljs-time.format :as time-format]
   ))

(defonce fs (js/require "fs"))

;; event cofx

(re-frame/reg-cofx
 :main-webview
 (fn-traced
  [cofx _]
  (assoc cofx :main-webview (.getElementById js/document "fgc-webview"))))

(re-frame/reg-cofx
 :screenshot-time-format
 (fn-traced
  [cofx _]
  (assoc cofx :screenshot-time-format (time-format/formatter "yyyyMMdd-hhmmss"))))

(re-frame/reg-cofx
 :now
 (fn-traced
  [cofx _]
  (assoc cofx :now (time/now))))

;; event handlers

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(re-frame/reg-event-fx
 ::initialize-webview
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [wv (:main-webview cofx)
        db (:db cofx)
        wv-css (get-in db [:config :fgc-css])]
    (timbre/spy wv-css)
    (.addEventListener wv "did-finish-load"
                       (fn [] (.insertCSS wv wv-css))))))

(re-frame/reg-event-fx
 ::take-screenshot
 [(re-frame/inject-cofx :main-webview)
  (re-frame/inject-cofx :screenshot-time-format)
  (re-frame/inject-cofx :now)]
 (fn-traced
  [cofx _]
  (let [db (:db cofx)
        file-path (get-in db [:config :screenshot-path])
        file-prefix (get-in db [:config :screenshot-prefix])
        current-time (time-format/unparse (:screenshot-time-format cofx) (:now cofx))
        wv (:main-webview cofx)]
    {:webview-screenshot
     {:full-file-path (str file-path file-prefix current-time ".png")
      :target-webview wv
      :on-success [:webview-screenshot-success]
      :on-failure [:webview-screenshot-failure]}})))

(re-frame/reg-event-db
 :webview-screenshot-success
 (fn-traced
  [db [ev eva]]
  (timbre/spy eva)
  (-> db
      (update-in [:state :notification] assoc :open true :type :normal :message "Screenshot Saved!")
      (as-> x (timbre/spy x))))
  )

(re-frame/reg-event-fx
 :webview-screenshot-failure
 (fn-traced
  [cofx ev]
  (timbre/spy ev)
  {}
  ))

(re-frame/reg-event-db
 ::close-notification
 (fn-traced
  [db _]
  (timbre/spy db)
  (-> db
      (update-in [:state :notification] assoc :open false :type :normal :message "")
      (as-> x (timbre/spy x)))))
