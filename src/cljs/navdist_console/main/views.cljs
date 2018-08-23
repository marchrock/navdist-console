(ns navdist-console.main.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [navdist-console.main.events :as events]
   [navdist-console.main.subs :as subs]
   [navdist-console.i18n :as i18n]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]
   ["material-ui/styles" :as mui-styles]
   ["material-ui/colors" :as mui-colors]
   ["material-ui-icons" :as mui-icons]
   ))

;; style

(def flex-style
  (clj->js {:flexGrow 1}))

(def app-bar-menu-style
  (clj->js {:marginLeft -18 :marginRight 10}))

;; web-view

(defn fgc-webview
  "Webview containing game view."
  []
  (let [uri (re-frame/subscribe [::subs/fgc-uri])]
    [:> mui/Grid {:class "fgc-div" :item true}
     [:webview#fgc-webview.fgc-webview {:src @uri}]]))

;; button

(defn fgc-screenshot-icon-button
  "App-bar screenshot button w/ camera icon."
  []
  [:> mui/IconButton {:className "screenshotButton" :color "inherit"
                      :aria-label "screenshot"
                      :on-click #(re-frame/dispatch [::events/take-screenshot])}
   [:> mui-icons/CameraAlt]])

(defn fgc-volume-toggle-icon-button
  "App-bar volume toggle button w/ volume icon."
  []
  (let [state (re-frame/subscribe [::subs/state-mute])]
    [:> mui/IconButton {:className "muteButton" :color "inherit"
                        :aria-label "mute"
                        :on-click #(re-frame/dispatch [::events/toggle-volume])}
     (if @state
       [:> mui-icons/VolumeOff]
       [:> mui-icons/VolumeUp])]
    ))

;; snackbar

(defn notification-bar
  "Global notification bar subscribing ::state-notification."
  []
  (let [state (re-frame/subscribe [::subs/state-notification])]
    [:> mui/Snackbar {:anchorOrigin (clj->js {:vertical "bottom" :horizontal "left"})
                      :open (get-in @state [:open])
                      :on-close #(re-frame/dispatch [::events/close-notification])
                      :autoHideDuration 2000
                      :message (get-in @state [:message])}]
    ))

;; panels

(defn confirm-shutdown-dialog
  []
  (let [state (re-frame/subscribe [::subs/state-dialog-shutdown])]
    (timbre/spy @state)
    [:> mui/Dialog {:open @state
                    :on-close #(re-frame/dispatch [::events/close-shutdown-dialog])}
     [:> mui/DialogTitle
      (i18n/tr-nd [:dialog/shutdown])]
     [:> mui/DialogActions
      [:> mui/Button {:on-click #(re-frame/dispatch [::events/close-shutdown-dialog])
                      :autoFocus true}
       (i18n/tr-nd [:button/cancel])]
      [:> mui/Button {:on-click #(re-frame/dispatch [::events/shutdown-app])
                      :color "secondary"}
       (i18n/tr-nd [:button/shutdown])]]]))

(defn app-menu-drawer
  []
  (let [state (re-frame/subscribe [::subs/state-menu-drawer])]
    [:div
     [:> mui/Drawer {:open (get-in @state [:open])
                     :on-close #(re-frame/dispatch [::events/menu-drawer-close])}
      [:div {:tabIndex 0 :role "button"
             :on-click #(re-frame/dispatch [::events/menu-drawer-close])
             :on-keyDown #(re-frame/dispatch [::events/menu-drawer-close])}
       [:> mui/Toolbar {:variant "dense"}
        [:> mui/Typography {:variant "subheading" :color "inherit" :style flex-style}
         "Navdist Menu"]]
       [:> mui/List
        [:> mui/ListItem {:button true
                          :on-click #(re-frame/dispatch [::events/open-shutdown-dialog])}
         [:> mui/ListItemIcon
          [:> mui-icons/Settings]]
         [:> mui/ListItemText {:primary (i18n/tr-nd [:global-menu/configure])}]]
        ]
       [:> mui/Divider]
       [:> mui/List
        [:> mui/ListItem {:button true
                          :on-click #(re-frame/dispatch [::events/open-shutdown-dialog])}
         [:> mui/ListItemIcon
          [:> mui-icons/PowerSettingsNew]]
         [:> mui/ListItemText {:primary (i18n/tr-nd [:global-menu/shutdown])}]]
        ]
       ]]
     [confirm-shutdown-dialog]
     ]
    ))

(defn app-bar
  "Top app-bar with Global menu, app title, and basic feature button."
  []
  [:> mui/AppBar {:id "global-app-bar" :position "static"}
   [:> mui/Toolbar {:id "drag-region" :variant "dense"}
    [:> mui/IconButton {:className "no-drag-region" :color "inherit" :aria-label "menu"
                        :on-click #(re-frame/dispatch [::events/menu-drawer-open])
                        :style app-bar-menu-style}
     [:> mui-icons/Menu]]
    [:> mui/Typography {:variant "title" :color "inherit" :style flex-style}
     "Navdist Console"]
    [:div {:className "no-drag-region"}
     [fgc-screenshot-icon-button]
     [fgc-volume-toggle-icon-button]]
    ]])

(defn left-down-panel
  "Left side down panel for future usage"
  []
  [:> mui/Grid {:item true :container true}])

(defn left-panel
  "Left panel for app. Main panel containing game webview"
  []
  [:> mui/Grid {:container true :item true :direction "column"}
   [fgc-webview]
   [left-down-panel]])

(defn right-panel
  "Right panel for app. Expected to be used in Extension mode"
  []
  [:> mui/Grid {:container true :item true}])

(defn main-panel
  "Main panel for app"
  []
  [:div {:class "root" :style flex-style}
   [app-bar]
   [app-menu-drawer]
   [:> mui/Grid {:container true :direction "row"}
    [left-panel]
    [right-panel]
    [notification-bar]]
   ])
