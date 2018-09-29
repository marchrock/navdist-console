(ns navdist.app.cofx.core
  (:require
   [re-frame.core :as re-frame]
   [cljs-time.local :as time-local]
   [taoensso.timbre :as timbre]
   ["electron" :refer [remote]]))

(re-frame/reg-cofx
 :os-home-dir
 (fn [cofx]
   (let [app (-> remote .-app)]
     (assoc cofx :os-home-dir (.getPath app "home")))))

(re-frame/reg-cofx
 :user-data-dir
 (fn [cofx]
   (let [app (-> remote .-app)]
     (assoc cofx :user-data-dir (.getPath app "userData")))))

(re-frame/reg-cofx
 :current-window
 (fn [cofx]
   (let [window (-> remote .getCurrentWindow)]
     (assoc cofx :current-window window))))

(re-frame/reg-cofx
 :app-webview
 (fn [cofx]
   (let [app-webview (.getElementById js/document "app-webview")]
     (assoc cofx :app-webview app-webview))))

(re-frame/reg-cofx
 :now
 (fn [cofx]
   (assoc cofx :now (time-local/local-now))))
