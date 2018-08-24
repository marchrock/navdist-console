(ns navdist-console.components.webview
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub]]
   ["material-ui" :as mui]))

(def app-webview-style
  (clj->js {:width "1200px" :height "720px"}))

(defn app-webview
  []
  [:> mui/Grid {:item true}
   [:webview {:id "app-webview"
              :style app-webview-style
              :src (<sub [:config-uri])}]])
