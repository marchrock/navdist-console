(ns navdist.app.views.molecules.dialog
  (:require
   [navdist.app.helper :refer [<sub >evt]]
   [navdist.app.views.atoms.button :as button]
   ["@material-ui/core" :as mui]))

(defn confirm-shutdown
  []
  [:> mui/Dialog {:open (<sub [:state-dialog-shutdown])}
   [:> mui/DialogTitle
    "Shutdown"]
   [:> mui/DialogActions
    [button/string-button "Cancel"
     {:on-click #(>evt [:toggle-dialog-confirm-shutdown {:open false}])}]
    [button/string-button "Shutdown" {:color "secondary"
                                      :on-click #(>evt [:close-window])}]]])
