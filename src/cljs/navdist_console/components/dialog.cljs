(ns navdist-console.components.dialog
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.components.button :as button]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.i18n :as i18n]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]))

(defn confirm-shutdown
  []
  [:> mui/Dialog {:open (timbre/spy (<sub [:state-dialog-shutdown]))}
   [:> mui/DialogTitle
    (i18n/tr-nd [:dialog/shutdown])]
   [:> mui/DialogActions
    [button/str-button :button/cancel [:toggle-dialog-shutdown false]]
    [button/str-button :button/shutdown [:close-app] {:color "secondary"}]]])
