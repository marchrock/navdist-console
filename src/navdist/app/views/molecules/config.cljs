(ns navdist.app.views.molecules.config
  (:require
   [navdist.app.views.atoms.button :as b]
   [navdist.app.views.atoms.selector :as selector]
   [navdist.app.styles :as s]
   [navdist.app.helper :refer [<sub >evt >evtm]]
   [navdist.app.i18n :as i18n :refer [tr]]
   [reagent.core :as r]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]
   ["@material-ui/icons" :as mui-icons]))

(defn config-title
  []
  [:> mui/Typography {:variant "title"
                      :color "inherit"
                      :style s/grow}
   (tr [:config/title])])

(defn config-control
  []
  [:div {:style s/no-drag-region}
   [b/string-button [:button/cancel]
    {:color "inherit"
     :on-click #(>evt [:toggle-config-panel {:open false}])}]
   [b/string-button [:button/save]
    {:color "inherit"
     :on-click #(>evt [:toggle-config-panel {:open false}])}]])

(defn config-locale
  []
  (let [current-locale (<sub [:config-locale])]
    [:> mui/ListItem
     [:> mui/ListItemIcon
      [:> mui-icons/Language]]
     [:> mui/ListItemText {:primary (tr [:config/locale])}]
     [:> mui/ListItemSecondaryAction
      [selector/direct current-locale i18n/navdist-locale-list
       (selector/MenuKvl. :key :key :label)
       {:style (clj->js {:width "100%"})
        :on-change #(>evt [:config-locale {:value (-> % .-target .-value (timbre/spy))}])}]]]))

(defn config-list
  []
  [:> mui/List
   [config-locale]])

(defn config-panel
  []
  (let [state (<sub [:state-config-panel])]
    [:> mui/Dialog {:open (:open state)
                    :fullScreen true}
     [:> mui/AppBar {:position "static"
                     :style (merge s/drag-region)}
      [:> mui/Toolbar {:variant "dense"}
       [config-title]
       [config-control]]]
     [config-list]]))
