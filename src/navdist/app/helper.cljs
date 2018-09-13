(ns navdist.app.helper
  (:require
   [re-frame.core :as re-frame]))

(def <sub (comp deref re-frame/subscribe))

(def >evt re-frame/dispatch)
