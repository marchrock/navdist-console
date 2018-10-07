(ns navdist.app.effects.shutdown
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

(defn-traced shutdown-window
  [req]
  (let [window (:current-window req)]
    (.close window)))

(re-frame/reg-fx
 :shutdown-window
 shutdown-window)
