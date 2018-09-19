(ns navdist.app.views.molecules.notification
  (:require
   [re-frame.core :as re-frame]
   [navdist.app.i18n :refer [tr]]
   [navdist.app.helper :refer [<sub >evt]]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]))

(def notification-anchor
  (clj->js {:vertical "bottom" :horizontal "left"}))

(def notification-duration 2000)

(defn notification-bar
  []
  (let [state (<sub [:state-notification])
        message-key (get-in state [:message])]
    (timbre/spy message-key)
    [:> mui/Snackbar {:anchorOrigin notification-anchor
                      :open (:open state)
                      :on-close #(>evt [:toggle-notification {:open false}])
                      :autoHideDuration notification-duration
                      :message (if (-> message-key first keyword?)
                                 (tr message-key)
                                 message-key)}]))
