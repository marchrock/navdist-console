(ns navdist.app.views.organisms.webview
  (:require
   [navdist.app.helper :refer [<sub]]
   ["@material-ui/core" :as mui]))

(def app-webview-style
  {100 {:width "1200px" :height "720px"}
   75 {:width "900px" :height "540px"}
   66 {:width "800px" :height "480px"}})

(defn app-webview
  []
  [:> mui/Paper
   [:webview {:id "app-webview"
              :style (-> (merge (get-in app-webview-style [(<sub [:config-zoom-factor])])
                                {:maxWidth "1200px" :maxHeight "720px"})
                         (clj->js))
              :src (<sub [:config-uri])}]])
