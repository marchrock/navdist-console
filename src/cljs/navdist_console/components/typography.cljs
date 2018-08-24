(ns navdist-console.components.typography
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub]]
   [navdist-console.components.button :as button]
   ["material-ui" :as mui]))

(def typography-style
  (clj->js {:flexGrow 1}))

(defn app-title
  []
  [:> mui/Typography {:variant "title"
                      :style typography-style
                      :color "inherit"}
   (<sub [:config-title])])
