(ns navdist.app.views.molecules.dialog
  (:require
   [navdist.app.i18n :refer [tr]]
   [navdist.app.helper :refer [<sub >evt]]
   [navdist.app.views.atoms.button :as button]
   ["@material-ui/core" :as mui]))

(defn confirm-shutdown
  []
  [:> mui/Dialog {:open (<sub [:state-dialog-shutdown])}
   [:> mui/DialogTitle
    (tr [:dialog/shutdown])]
   [:> mui/DialogActions
    [button/string-button [:button/cancel]
     {:on-click #(>evt [:toggle-dialog-confirm-shutdown {:open false}])}]
    [button/string-button [:button/shutdown]
     {:color "secondary"
      :on-click #(>evt [:close-window])}]]])
