(ns navdist.app.helper
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]))

(def <sub (comp deref re-frame/subscribe))

(def >evt re-frame/dispatch)

(defn >evtm
  [& evt]
  (timbre/spy
   (map re-frame/dispatch evt)))
