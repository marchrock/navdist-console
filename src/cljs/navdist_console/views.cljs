(ns navdist-console.views
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   [navdist-console.components.dialog :as dialog]
   [navdist-console.components.webview :refer [app-webview]]
   [navdist-console.panels.app-bar :refer [app-bar]]
   [navdist-console.panels.app-menu :refer [app-menu]]
   [navdist-console.panels.notifications :refer [notification-panel]]
   ["material-ui" :as mui]
   ["material-ui/styles" :as mui-styles]
   ["material-ui/colors" :as mui-colors]
   ["material-ui-icons" :as mui-icons]))

;; styles
(def app-theme
  (mui-styles/createMuiTheme))

;; panels
(defn left-info-panel
  []
  [:> mui/Grid {:container true :item true :direction "row"}
   [:div]])

(defn left-panel
  []
  [:> mui/Grid {:container true :item true :direction "column"}
   [app-webview]
   [left-info-panel]])

(defn right-panel
  []
  [:> mui/Grid {:container true :item true}
   [:div]])

(defn main-panel
  []
  [:> mui/Grid {:container true :direction "row"}
   [left-panel]
   [right-panel]])

(defn dialog-panel
  []
  [:div
   [dialog/confirm-shutdown]])

(defn top-panel
  []
  [:div
   [app-bar]
   [app-menu]])

(defn app-panel
  []
  [:> mui-styles/MuiThemeProvider {:theme app-theme}
   [top-panel]
   [main-panel]
   [dialog-panel]
   [notification-panel]])
