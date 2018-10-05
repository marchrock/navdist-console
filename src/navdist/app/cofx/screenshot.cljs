(ns navdist.app.cofx.screenshot
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]
   ["electron" :as electron]))

(defn-traced screenshot-directory
  [cofx]
  (let [dialog (-> electron .-remote .-dialog)
        path (-> (.showOpenDialog dialog nil (clj->js {:properties ["openDirectory"]}))
                 str)]
    (timbre/spy path)
    (assoc cofx :screenshot-directory path)))

(re-frame/reg-cofx
 :screenshot-directory
 screenshot-directory)
