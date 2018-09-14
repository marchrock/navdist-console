(ns navdist.app.views.atoms.menu
  (:require
   ["@material-ui/core" :as mui]))

(defn icon-menu-item
  [label icon & params]
  (let [custom-params (first params)]
    [:> mui/MenuItem custom-params
     [:> mui/ListItemIcon
      icon]
     [:> mui/ListItemText {:primary label :inset true}]]))
