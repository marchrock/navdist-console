(ns navdist-console.panels.settings
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.i18n :as i18n]
   [navdist-console.components.button :as button]
   [navdist-console.components.typography :as typography]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]
   ["material-ui-icons" :as mui-icons]))

(def settings-select-field-style
  (clj->js {:width 200 :margin 8}))

(def settings-path-field-style
  (clj->js {:width 400 :margin 8}))

(defn locale-menu-item
  [locale]
  [:> mui/MenuItem {:key (name locale) :value (name locale)}
   [i18n/tr-lc [:locale-name] locale]])

(defn locale-selector
  []
  (let [current-locale (<sub [:config-locale])]
    (timbre/spy current-locale)
    [:> mui/TextField {:style settings-select-field-style
                       :select true
                       :value (name current-locale)
                       :on-change #(>evt [:settings-locale (-> % .-target .-value)])}
     (map #(locale-menu-item %) i18n/navdist-locale-list)]))

(defn locale-settings
  []
  [:> mui/ListItem
   [:> mui/ListItemIcon
    [:> mui-icons/Language]]
   [:> mui/ListItemText {:primary (i18n/tr-nd [:settings/locale])}]
   [:> mui/ListItemSecondaryAction
    [locale-selector]]])

(defn screenshot-settings
  []
  [:> mui/ListItem
   [:> mui/ListItemIcon
    [:> mui-icons/Folder]]
   [:> mui/ListItemText {:primary (i18n/tr-nd [:settings/screenshot-path])}]
   [:> mui/ListItemSecondaryAction
    [:> mui/TextField {:style settings-path-field-style
                       :value (get-in (<sub [:config-screenshot]) [:path])}]
    [button/str-button :button/change [:open-screenshot-path-dialog]
     {:color "primary" :variant "outlined" :style (clj->js {:margin 8})}]]])

(defn settings-list
  []
  [:> mui/List {:style (clj->js {:margin 8})}
   [screenshot-settings]
   [locale-settings]])

(defn settings-panel-top-bar
  []
  [:> mui/AppBar {:position "static"}
   [:> mui/Toolbar {:position "static" :variant "dense"}
    [button/close :toggle-settings]
    [typography/settings-title]]])

(defn settings-panel
  []
  (let [open? (get-in (<sub [:state-settings]) [:open])]
    (timbre/spy open?)
    [:> mui/Dialog {:fullScreen true
                    :disableBackdropClick true
                    :disableEscapeKeyDown true
                    :open open?
                    :on-close #(>evt [:toggle-settings [(not open?)]])}
     [settings-panel-top-bar]
     [settings-list]]))
