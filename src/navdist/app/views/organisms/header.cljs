(ns navdist.app.views.organisms.header
  (:require
   [navdist.app.views.molecules.webview-control :refer [webview-control]]
   ["@material-ui/core" :as mui]))

(defn header-app-bar
  []
  [:> mui/AppBar {:position "static"}
   [:> mui/Toolbar {:variant "dense"}
    [webview-control]]])
