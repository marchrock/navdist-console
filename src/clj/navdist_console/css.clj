(ns navdist-console.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:margin 0 :padding 0 :border 0}]
  [:.fgc-div {:max-width "1200px" :max-height "720px" :margin 0 :padding 0}]
  [:.fgc-webview {:width "1200px" :height "720px" :margin 0 :padding 0}]
)
