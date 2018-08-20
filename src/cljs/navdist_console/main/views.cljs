(ns navdist-console.main.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [navdist-console.main.events :as events]
   [navdist-console.main.subs :as subs]
   ["material-ui" :as mui]
   ["material-ui/styles" :as mui-styles]
   ["material-ui/colors" :as mui-colors]
   ["material-ui-icons" :as mui-icons]
   ))

;; state

(def flex-style
  (clj->js {:flexGrow 1}))


;; web-view

(defn fgc-webview []
  (let [uri (re-frame/subscribe [::subs/fgc-uri])]
    [:> mui/Grid {:class "fgc-div" :item true}
     [:webview#fgc-webview.fgc-webview {:src @uri}]]))

(defn fgc-screenshot-icon-button []
  [:> mui/IconButton {:className "screenshotButton" :color "inherit"
                      :aria-label "screenshot"
                      :on-click #(re-frame/dispatch [::events/take-screenshot])}
   [:> mui-icons/CameraAlt]])

(defn fgc-mute-icon-button []
  (let [state (re-frame/subscribe [::subs/state-mute])]
    [:> mui/IconButton {:className "muteButton" :color "inherit"
                        :aria-label "mute"
                        :on-click #(re-frame/dispatch [::events/toggle-volume])}
     (if @state
       [:> mui-icons/VolumeOff]
       [:> mui-icons/VolumeUp])]
    ))

(defn notification-bar []
  (let [state (re-frame/subscribe [::subs/state-notification])]
    [:> mui/Snackbar {:anchorOrigin (clj->js {:vertical "bottom" :horizontal "left"})
                      :open (get-in @state [:open])
                      :on-close #(re-frame/dispatch [::events/close-notification])
                      :autoHideDuration 2000
                      :message (get-in @state [:message])}]
    ))

;; main

(defn app-bar []
  [:> mui/AppBar {:position "static"}
   [:> mui/Toolbar {:variant "dense"}
    [:> mui/IconButton {:className "menuButton" :color "inherit" :aria-label "menu"
                        :style (clj->js {:marginLeft -18 :marginRight 10})}
     [:> mui-icons/Menu]]
    [:> mui/Typography {:variant "title" :color "inherit" :style flex-style}
     "Navdist Console"]
    [fgc-screenshot-icon-button]
    [fgc-mute-icon-button]
    ]])

(defn left-down-panel []
  [:> mui/Grid {:item true :container true}])

(defn left-panel []
  [:> mui/Grid {:container true :item true :direction "column"}
   [fgc-webview]
   [left-down-panel]])

(defn right-panel []
  [:> mui/Grid {:container true :item true}])

(defn main-panel []
  [:div {:class "root" :style (clj->js {:flexGrow 1})}
   [app-bar]
   [:> mui/Grid {:container true :direction "row"}
    [left-panel]
    [right-panel]
    [notification-bar]]
   ]
  )
