(ns navdist-console.cofx
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [cljs-time.core :as time]
   [taoensso.timbre :as timbre]))

;; get main webview from dom and assoc to cofx
;; use webview via (:main-webview cofx)
(re-frame/reg-cofx
 :main-webview
 (fn-traced
  [cofx _]
  (assoc cofx :main-webview (.getElementById js/document "app-webview"))))

;; get current electron window
(re-frame/reg-cofx
 :current-app
 (fn-traced
  [cofx _]
  (let [electron (js/require "electron")
        remote (.-remote electron)
        window (.getCurrentWindow remote)]
    (assoc cofx :current-app window))))

;; get current time
(re-frame/reg-cofx
 :now
 (fn-traced
  [cofx _]
  (assoc cofx :now (time/now))))

;; get userdata directory
(re-frame/reg-cofx
 :user-data-dir
 (fn-traced
  [cofx]
  (let [electron (js/require "electron")
        remote (.-remote electron)
        app (.-app remote)]
    (assoc cofx :user-data-dir (.getPath app "userData")))))

;; get os home-dir
(re-frame/reg-cofx
 :os-home-dir
 (fn-traced
  [cofx]
  (let [os (js/require "os")]
    (assoc cofx :os-home-dir (.homedir os)))))
