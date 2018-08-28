(ns navdist-console.components.webview
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub]]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]))

(def app-webview-style
  {100 (clj->js {:width "1200px" :height "720px"})
   75 (clj->js {:width "900px" :height "540px"})
   66 (clj->js {:width "800px" :height "480px"})})

(defn app-webview
  []
  (let [zoom-factor (timbre/spy (<sub [:config-zoom-factor]))]
    [:> mui/Grid {:item true}
     [:webview {:id "app-webview"
                :style (get-in app-webview-style [zoom-factor])
                :src (<sub [:config-uri])}]]))
