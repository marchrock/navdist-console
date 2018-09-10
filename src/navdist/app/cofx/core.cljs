(ns navdist.app.cofx.core
  (:require
   [re-frame.core :as re-frame]
   ["electron" :refer [remote]]))

(re-frame/reg-cofx
 :os-home-dir
 (fn [cofx]
   (let [app (-> remote .-app)]
     (assoc cofx :os-home-dir (.getPath app "home")))))
