(ns navdist-console.components.typography
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub]]
   [navdist-console.i18n :as i18n]
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

(defn settings-title
  []
  [:> mui/Typography {:variant "title"
                      :style typography-style
                      :color "inherit"}
   (i18n/tr-nd [:app-menu/settings])])

(defn i18n-heading
  [string-key]
  [:> mui/Typography {:variant "subheading"}
   (i18n/tr-nd [string-key])])
