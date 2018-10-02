(ns navdist.app.views.molecules.webview-control
  (:require
   [navdist.app.styles :as s]
   [navdist.app.helper :refer [<sub >evt >evtm]]
   [navdist.app.views.atoms.icon-button :as ib]
   [navdist.app.views.atoms.menu :as menu]
   [navdist.app.i18n :refer [tr]]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]
   ["@material-ui/icons" :as mui-icons]))

(defn webview-control-overflow-menu
  []
  (let [state (timbre/spy (<sub [:state-app-bar-menu]))]
    [:> mui/Menu {:open (:open state)
                  :anchorEl (:anchor state)
                  :on-close #(>evt [:toggle-webview-control-menu {:open false}])}
     [menu/icon-menu-item [:app-bar-menu/zoom] [:> mui-icons/ZoomIn]
      {:on-click #(>evtm [:toggle-dialog-zoom-factor {:open true}]
                         [:toggle-webview-control-menu {:open false}])}]
     [menu/icon-menu-item [:app-bar-menu/settings] [:> mui-icons/Settings]
      {:on-click #(>evtm [:toggle-config-panel {:open true}]
                         [:toggle-webview-control-menu {:open false}])}]
     [:> mui/Divider]
     [menu/icon-menu-item [:app-bar-menu/reload] [:> mui-icons/Refresh]
      {:on-click #(>evtm [:webview-reload]
                         [:toggle-webview-control-menu {:open false}])}]
     [:> mui/Divider]
     [menu/icon-menu-item [:app-bar-menu/shutdown] [:> mui-icons/PowerSettingsNew]
      {:on-click #(>evtm [:toggle-dialog-confirm-shutdown {:open true}]
                         [:toggle-webview-control-menu {:open false}])}]]))

(defn webview-control
  []
  [:div {:style s/no-drag-region}
   [ib/screenshot {:on-click #(>evt [:webview-screenshot])}]
   [ib/toggle-volume [:state-volume] {:on-click #(>evt [:webview-volume])}]
   [ib/overflow-menu {:on-click
                      #(>evt [:toggle-webview-control-menu {:open true :anchor (-> % .-target)}])}]
   [webview-control-overflow-menu]])
