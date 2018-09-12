(ns navdist.app.views
  (:require
   ["@material-ui/core/styles" :as mui-styles]
   [navdist.app.styles :as s]
   [navdist.app.views.organisms.header :refer [header-app-bar]]))

(defn header
  []
  [:div
   [header-app-bar]])

(defn footer
  []
  [:div])

(defn app-view
  []
  [:> mui-styles/MuiThemeProvider {:theme s/app-theme}
   [header]
   [footer]])
