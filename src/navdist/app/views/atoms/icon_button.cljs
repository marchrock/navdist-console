(ns navdist.app.views.atoms.icon-button
  (:require
   [re-frame.core :as re-frame]
   [navdist.app.helper :refer [<sub >evt]]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]
   ["@material-ui/icons" :as mui-icons]))

(def icon-button-param
  {:color "inherit"})

(defn screenshot
  "Screenshot icon button"
  [& params]
  (let [custom-param (first params)
        p (merge icon-button-param custom-param)]
    [:> mui/IconButton p
     [:> mui-icons/CameraAlt]]))

(defn toggle-volume
  "Volume toggling icon button"
  [volume-state-sub & params]
  (let [custom-param (first params)
        p (merge icon-button-param custom-param)]
    [:> mui/IconButton p
     (if (timbre/spy (<sub volume-state-sub))
       [:> mui-icons/VolumeUp]
       [:> mui-icons/VolumeOff])]))

(defn reload
  "Reload icon button"
  [disabled? & params]
   (let [custom-param (first params)
         p (merge icon-button-param custom-param {:disabled disabled?})]
     [:> mui/IconButton p
      [:> mui-icons/Refresh]]))
