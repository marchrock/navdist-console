(ns navdist.app.views.organisms.header
  (:require
   [navdist.app.styles :as s]
   [navdist.app.views.molecules.webview-control :refer [webview-control]]
   ["@material-ui/core" :as mui]))

(defn header-title
  []
  [:> mui/Typography {:variant "title"
                      :color "inherit"
                      :style s/grow}
   "Navdist Console"])

(defn header-app-bar
  []
  [:> mui/AppBar {:position "static"
                  :style (merge s/grow s/drag-region)}
   [:> mui/Toolbar {:variant "dense"}
    [header-title]
    [webview-control]]])
