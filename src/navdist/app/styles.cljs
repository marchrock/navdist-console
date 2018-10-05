(ns navdist.app.styles
  (:require
   ["@material-ui/core/styles" :as mui-styles]
   ["@material-ui/core/colors" :as mui-colors]))

(def app-theme
  (mui-styles/createMuiTheme
   (clj->js {:palette {:primary mui-colors/indigo
                       :secondary mui-colors/red}})))

(def drag-region
  {:-webkit-app-region "drag"})

(def no-drag-region
  {:-webkit-app-region "no-drag"})

(def grow
  {:flexGrow 1})

(def config-action-size
  {:width "300px"})
