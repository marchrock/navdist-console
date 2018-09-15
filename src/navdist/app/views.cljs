(ns navdist.app.views
  (:require
   ["@material-ui/core/styles" :as mui-styles]
   [navdist.app.styles :as s]
   [navdist.app.views.organisms.header :refer [header-app-bar]]
   [navdist.app.views.organisms.floating :refer [dialogs]]))

(defn header
  []
  [:div
   [header-app-bar]])

(defn footer
  []
  [:div])

(defn staging-area
  []
  [:span
   [dialogs]])

(defn app-view
  []
  [:> mui-styles/MuiThemeProvider {:theme s/app-theme}
   [header]
   [footer]
   [staging-area]])
