(ns navdist-console.main.views
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.events :as events]
   [navdist-console.main.subs :as subs]
   ["material-ui" :as mui]
   ["material-ui/styles" :as mui-styles]
   ["material-ui/colors" :as mui-colors]
   ["material-ui-icons" :as mui-icons]
   ))

;; web-view

(defn fgc-webview []
  (let [uri (re-frame/subscribe [::subs/fgc-uri])]
    [:> mui/Grid {:class "fgc-div" :item true}
     [:webview#fgc-webview.fgc-webview {:src @uri}]]))

(defn fgc-screenshot-button []
  [:> mui/Grid {:item true}
   [:> mui/Button {:on-click #(re-frame/dispatch [::events/take-screenshot])}
    "Screen Shot"]])

(defn fgc-mute-button []
  [:> mui/Grid {:item true}
   [:> mui/Button "Mute"]])

(defn notification-bar []
  (let [state (re-frame/subscribe [::subs/state-notification])]
    [:> mui/Snackbar {:anchorOrigin (clj->js {:vertical "bottom" :horizontal "left"})
                      :open (get-in @state [:open])
                      :on-close #(re-frame/dispatch [::events/close-notification])
                      :autoHideDuration 2000
                      :message (get-in @state [:message])}]
    ))

;; main

(defn left-down-panel []
  [:> mui/Grid {:item true :container true}])

(defn left-panel []
  [:> mui/Grid {:container true :item true :direction "column"}
   [fgc-webview]
   [:> mui/Grid {:item true :container true :direction "row" :justify "flex-start"}
    [fgc-screenshot-button]
    [fgc-mute-button]]
   [left-down-panel]])

(defn right-panel []
  [:> mui/Grid {:container true :item true}])

(defn main-panel []
  [:> mui/Grid {:container true :direction "row"}
   [left-panel]
   [right-panel]
   [notification-bar]
   ])
