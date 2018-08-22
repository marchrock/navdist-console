(ns navdist-console.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:margin 0 :padding 0 :border 0}]
  [:#global-app-bar {:-webkit-app-region "drag"}]
  [:#drag-region {:-webkit-app-region "drag"}]
  [:.no-drag-region {:-webkit-app-region "no-drag"}]
  [:.fgc-div {:max-width "1200px" :max-height "720px" :margin 0 :padding 0}]
  [:.fgc-webview {:width "1200px" :height "720px" :margin 0 :padding 0}]
)
