(ns navdist-console.effects.shutdown
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]))

(defn shutdown-app
  [req]
  (let [window (:current-app req)]
    (.close window)))

(defn-traced shutdown-handler
  [req]
  (timbre/spy req)
  (-> req
      shutdown-app))

(re-frame/reg-fx
 :shutdown-app
 shutdown-handler)
