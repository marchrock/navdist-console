(ns navdist.app.views
  (:require
   ["@material-ui/core/styles" :as mui-styles]
   [navdist.app.styles :as s]
   [navdist.app.views.organisms.webview :refer [app-webview]]
   [navdist.app.views.organisms.header :refer [header-app-bar]]
   [navdist.app.views.organisms.floating :as f]))

(defn header
  []
  [:div
   [header-app-bar]])

(defn content
  []
  [:div
   [app-webview]])

(defn footer
  []
  [:div])

(defn staging-area
  []
  [:span
   [f/dialogs]
   [f/notification]])

(defn app-view
  []
  [:> mui-styles/MuiThemeProvider {:theme s/app-theme}
   [header]
   [content]
   [footer]
   [staging-area]])
