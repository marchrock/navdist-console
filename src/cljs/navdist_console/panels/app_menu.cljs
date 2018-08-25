(ns navdist-console.panels.app-menu
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.i18n :as i18n]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.components.button :as button]
   [navdist-console.components.typography :as typography]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]
   ["material-ui-icons" :as mui-icons]))

(defn settings-item
  []
  [:> mui/ListItem {:button true
                    :on-click #(>evt [:toggle-settings true])}
   [:> mui/ListItemIcon
    [:> mui-icons/Settings]]
   [:> mui/ListItemText {:primary (i18n/tr-nd [:app-menu/settings])}]])

(defn shutdown-item
  []
  [:> mui/ListItem {:button true
                    :on-click #(>evt [:toggle-dialog-shutdown true])}
   [:> mui/ListItemIcon
    [:> mui-icons/PowerSettingsNew]]
   [:> mui/ListItemText {:primary (i18n/tr-nd [:app-menu/shutdown])}]])

(defn upper-list
  []
  [:> mui/List
   [settings-item]])

(defn lower-list
  []
  [:> mui/List
   [shutdown-item]])

(defn app-menu
  []
  [:div
   [:> mui/Drawer {:open (timbre/spy (get-in (<sub [:state-app-menu]) [:open]))
                   :on-close #(>evt [:toggle-app-menu false])}
    [:div {:className "no-drag-region" :role "button"
           :on-click #(>evt [:toggle-app-menu false])
           :on-keyDown #(>evt [:toggle-app-menu false])}
     [:> mui/Toolbar {:variant "dense"}
      [button/app-menu false]
      [typography/app-title]]
     [:> mui/Divider]
     [upper-list]
     [:> mui/Divider]
     [lower-list]]]])
