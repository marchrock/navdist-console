(ns navdist-console.panels.notifications
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.i18n :as i18n]
   ["material-ui" :as mui]))

(def notification-anchor
  (clj->js {:vertical "bottom" :horizontal "left"}))

;; global notification panel
(defn notification-bar
  []
  (let [state (<sub [:state-app-notification])
        message-key (get-in state [:message])]
    (timbre/spy message-key)
    [:> mui/Snackbar {:anchorOrigin notification-anchor
                      :open (get-in state [:open])
                      :on-close #(>evt [:close-notification])
                      :autoHideDuration (get-in state [:duration])
                      :message (if (keyword? (first message-key))
                                 (i18n/tr-nd message-key)
                                 message-key)}]))

;; panel
(defn notification-panel
  "Virtual panel to contain all notification bar"
  []
  [:div
   [notification-bar]])
