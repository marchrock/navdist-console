(ns navdist-console.panels.app-bar
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub]]
   [navdist-console.components.button :as button]
   [navdist-console.components.typography :as typography]
   ["material-ui" :as mui]))

(defn app-bar
  []
  [:> mui/AppBar {:id "global-app-bar" :position "static"}
   [:> mui/Toolbar {:id "drag-region" :variant "dense"}
    [button/app-menu true]
    [typography/app-title]
    [:div {:className "no-drag-region"}
     [button/screenshot]
     [button/toggle-volume]
     (let [reload-state (get-in (<sub [:state-app-bar]) [:reload-enabled])]
       [:span
        [button/switch-button reload-state [:toggle-app-bar-reload (not reload-state)]]
        [button/reload (not reload-state)]])]]])
