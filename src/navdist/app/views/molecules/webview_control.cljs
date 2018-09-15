(ns navdist.app.views.molecules.webview-control
  (:require
   [navdist.app.styles :as s]
   [navdist.app.helper :refer [<sub >evt]]
   [navdist.app.views.atoms.icon-button :as ib]
   [navdist.app.views.atoms.menu :as menu]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]
   ["@material-ui/icons" :as mui-icons]))

(defn webview-control-overflow-menu
  []
  (let [state (timbre/spy (<sub [:state-app-bar-menu]))]
    [:> mui/Menu {:open (:open state)
                  :anchorEl (:anchor state)
                  :on-close #(>evt [:toggle-webview-control-menu {:open false}])}
     [menu/icon-menu-item "Zoom" [:> mui-icons/ZoomIn]]
     [menu/icon-menu-item "Settings" [:> mui-icons/Settings]]
     [:> mui/Divider]
     [menu/icon-menu-item "Reload" [:> mui-icons/Refresh]]
     [:> mui/Divider]
     [menu/icon-menu-item "Shutdown" [:> mui-icons/PowerSettingsNew]]]))

(defn webview-control
  []
  [:div {:style s/no-drag-region}
   [ib/screenshot]
   [ib/toggle-volume [:state-volume]]
   [ib/overflow-menu {:on-click
                      #(>evt [:toggle-webview-control-menu {:open true :anchor (-> % .-target)}])}]
   [webview-control-overflow-menu]])
