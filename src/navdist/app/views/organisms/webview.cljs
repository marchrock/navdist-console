(ns navdist.app.views.organisms.webview
  (:require
   [navdist.app.helper :refer [<sub]]
   ["@material-ui/core" :as mui]))

(def app-webview-style
  {100 (clj->js {:width "1200px" :height "720px"})
   75 (clj->js {:width "900px" :height "540px"})
   66 (clj->js {:width "800px" :height "480px"})})

(defn app-webview
  []
  [:> mui/Paper
   [:webview {:id "app-webview"
              :style (get-in app-webview-style [100])
              :src (<sub [:config-uri])}]])
