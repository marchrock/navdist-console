(ns navdist.app.views.organisms.floating
  (:require
   [navdist.app.views.molecules.dialog :as d]))

(defn dialogs
  []
  [:span
   [d/confirm-shutdown]])
