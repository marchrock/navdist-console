(ns navdist.app.views.molecules.webview-control
  (:require
   [navdist.app.helper :refer [<sub]]
   [navdist.app.views.atoms.icon-button :as ib]
   ["@material-ui/core" :as mui]))

(defn danger-zone
  []
  (let [danger-zone-state (get-in (<sub [:state-app-bar]) [:dz-enabled])]
    [:span
     [ib/reload false]]))

(defn webview-control
  []
  [:div
   [ib/screenshot]
   [ib/toggle-volume [:state-volume]]
   [danger-zone]])
