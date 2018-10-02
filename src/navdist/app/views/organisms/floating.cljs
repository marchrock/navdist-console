(ns navdist.app.views.organisms.floating
  (:require
   [navdist.app.views.molecules.dialog :as d]
   [navdist.app.views.molecules.notification :as n]
   [navdist.app.views.molecules.config :as c]))

(defn dialogs
  []
  [:span
   [d/confirm-shutdown]
   [d/config-zoom-factor]
   [c/config-panel]])

(defn notification
  []
  [:span
   [n/notification-bar]])
