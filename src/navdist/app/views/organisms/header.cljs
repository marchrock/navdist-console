(ns navdist.app.views.organisms.header
  (:require
   ["@material-ui/core" :as mui]))

(defn header-app-bar
  []
  [:> mui/AppBar {:position "static"}
   [:> mui/Toolbar {:variant "dense"}]])
