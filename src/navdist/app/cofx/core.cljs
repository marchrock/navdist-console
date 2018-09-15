(ns navdist.app.cofx.core
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   ["electron" :refer [remote]]))

(re-frame/reg-cofx
 :os-home-dir
 (fn [cofx]
   (let [app (-> remote .-app)]
     (assoc cofx :os-home-dir (.getPath app "home")))))

(re-frame/reg-cofx
 :current-window
 (fn [cofx]
   (let [window (-> remote .getCurrentWindow)]
     (assoc cofx :current-window window))))
