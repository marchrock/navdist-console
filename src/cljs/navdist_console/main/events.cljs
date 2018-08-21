(ns navdist-console.main.events
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.db :as db]
   [navdist-console.main.subs :as subs]
   [navdist-console.main.screenshot]
   [navdist-console.main.volume]
   [navdist-console.main.shutdown]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre :refer-macros [spy info]]
   [cljs-time.core :as time]
   [cljs-time.format :as time-format]
   ))

;; event cofx

;; get main webview from dom and assoc to cofx
;; use webview via (:main-webview cofx)
(re-frame/reg-cofx
 :main-webview
 (fn-traced
  [cofx _]
  (assoc cofx :main-webview (.getElementById js/document "fgc-webview"))))

;; create screenshot time format and assoc to cofx
;; use webview via (:screenshot-time-format cofx)
(re-frame/reg-cofx
 :screenshot-time-format
 (fn-traced
  [cofx _]
  (assoc cofx :screenshot-time-format (time-format/formatter "yyyyMMdd-hhmmss"))))

;; get current time and assoc to cofx
;; use webview via (:now cofx)
(re-frame/reg-cofx
 :now
 (fn-traced
  [cofx _]
  (assoc cofx :now (time/now))))

;; get current electron window
(re-frame/reg-cofx
 :current-app
 (fn-traced
  [cofx _]
  (let [electron (js/require "electron")
        remote (.-remote electron)
        window (.getCurrentWindow remote)]
    (assoc cofx :current-app window))))

;; event handlers

;; initializing db
(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

;; initialize webview event to inject css to show only game screen in webview
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

;; event handler for volume toggling of webview
;; actual effect handling happens in navidst-console.main.volume
(re-frame/reg-event-fx
 ::toggle-volume
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [db (:db cofx)
        mute (get-in db [:state :mute])
        wv (:main-webview cofx)]
    (if mute
      {:toggle-volume-on {:mute false :target-webview wv}
       :db (assoc-in db [:state :mute] false)}
      {:toggle-volume-off {:mute true :target-webview wv}
       :db (assoc-in db [:state :mute] true)}))))

;; event handler for taking screenshot
;; actual effect handling happens in navdist-console.main.screenshot
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

;; event handler when taking screenshot succeeded
;; TODO: need more generalized notification event handler
(re-frame/reg-event-db
 :webview-screenshot-success
 (fn-traced
  [db [ev eva]]
  (timbre/spy eva)
  (-> db
      (update-in [:state :notification] assoc :open true :type :normal :message "Screenshot Saved!")
      (as-> x (timbre/spy x))))
  )

;; event handler when taking screenshot somehow failed
;; currently, does nothing
;; TODO: implement some error notification
(re-frame/reg-event-fx
 :webview-screenshot-failure
 (fn-traced
  [cofx ev]
  (timbre/spy ev)
  {}
  ))

;; db event handler for notification snackbar close
(re-frame/reg-event-db
 ::close-notification
 (fn-traced
  [db _]
  (timbre/spy db)
  (-> db
      (update-in [:state :notification] assoc :open false :type :normal :message "")
      (as-> x (timbre/spy x)))))

;; db event handler for menu drawer open
(re-frame/reg-event-db
 ::menu-drawer-open
 (fn-traced
  [db _]
  (-> db
      (update-in [:state :menu-drawer] assoc :open true)
      (as-> x (timbre/spy x)))))

;; db event handler for menu drawber close
(re-frame/reg-event-db
 ::menu-drawer-close
 (fn-traced
  [db _]
  (-> db
      (update-in [:state :menu-drawer] assoc :open false)
      (as-> x (timbre/spy x)))))

;; db event handler to show shutdown confirmation dialog
(re-frame/reg-event-db
 ::open-shutdown-dialog
 (fn-traced
  [db _]
  (timbre/debug "::open-shutdown-dialog")
  (-> db
      (update-in [:state :dialog] assoc :shutdown true))))

;; db event handler to hide shutdown confirmation dialog
(re-frame/reg-event-db
 ::close-shutdown-dialog
 (fn-traced
  [db _]
  (timbre/debug "::close-shutdown-dialog")
  (-> db
      (update-in [:state :dialog] assoc :shutdown false))))

;; event handler to shutdown app
(re-frame/reg-event-fx
 ::shutdown-app
 [(re-frame/inject-cofx :current-app)]
 (fn-traced
  [cofx _]
  {:shutdown-navdist-console {:current-app (:current-app cofx)}}))
