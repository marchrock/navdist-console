(ns navdist-console.css
  (:require
   [garden.def :refer [defstyles]]
   [garden.stylesheet :as garden-ss]))

(defstyles screen
  (garden-ss/at-import
   "https://fonts.googleapis.com/css?family=Roboto")
  [:body {:margin 0 :padding 0 :border 0
          :font-family "'Roboto', sans-serif"}]
  [:#global-app-bar {:-webkit-app-region "drag"}]
  [:#drag-region {:-webkit-app-region "drag"}]
  [:.no-drag-region {:-webkit-app-region "no-drag"}]
  [:.app-div {:max-width "1200px" :max-height "720px" :margin 0 :padding 0}]
)
