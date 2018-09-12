(ns navdist.app.styles
  (:require
   ["@material-ui/core/styles" :as mui-styles]
   ["@material-ui/core/colors" :as mui-colors]))

(def app-theme
  (mui-styles/createMuiTheme
   (clj->js {:palette {:primary mui-colors/lightBlue
                       :secondary mui-colors/red}})))
