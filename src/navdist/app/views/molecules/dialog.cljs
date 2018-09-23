(ns navdist.app.views.molecules.dialog
  (:require
   [navdist.app.i18n :refer [tr]]
   [navdist.app.helper :refer [<sub >evt >evtm]]
   [navdist.app.views.atoms.button :as button]
   [navdist.app.views.atoms.selector :as selector]
   [navdist.app.db :as db]
   [reagent.core :as r]
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

(defn config-zoom-factor
  []
  (let [current-zoom-factor (<sub [:config-zoom-factor])
        tmp-zoom-factor (r/atom current-zoom-factor)]
    [:> mui/Dialog {:open (<sub [:state-dialog-zoom-factor])}
     [:> mui/DialogTitle
      (tr [:dialog/zoom-factor])]
     [:> mui/DialogContent
      [selector/normal tmp-zoom-factor db/zoom-factor
       {:style (clj->js {:width "100%"})}]]
     [:> mui/DialogActions
      [button/string-button [:button/cancel]
       {:on-click #(>evt [:toggle-dialog-zoom-factor {:open false}])}]
      [button/string-button [:button/apply]
       {:on-click #(>evtm [:config-zoom-factor {:zoom-factor @tmp-zoom-factor}]
                          [:toggle-dialog-zoom-factor {:open false}])}]]]))
