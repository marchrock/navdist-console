(ns navdist.app.views.atoms.menu
  (:require
   [navdist.app.i18n :refer [tr]]
   ["@material-ui/core" :as mui]))

(defn icon-menu-item
  [label-key icon & params]
  (let [custom-params (first params)
        label (if (string? label-key)
                label-key
                (tr label-key))]
    [:> mui/MenuItem custom-params
     [:> mui/ListItemIcon
      icon]
     [:> mui/ListItemText {:primary label :inset true}]]))
