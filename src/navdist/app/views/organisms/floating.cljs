(ns navdist.app.views.organisms.floating
  (:require
   [navdist.app.views.molecules.dialog :as d]
   [navdist.app.views.molecules.notification :as n]))

(defn dialogs
  []
  [:span
   [d/confirm-shutdown]
   [d/config-zoom-factor]])

(defn notification
  []
  [:span
   [n/notification-bar]])
