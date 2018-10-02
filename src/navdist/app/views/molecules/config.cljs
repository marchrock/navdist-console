(ns navdist.app.views.molecules.config
  (:require
   [navdist.app.views.atoms.button :as b]
   [navdist.app.styles :as s]
   [navdist.app.helper :refer [<sub >evt >evtm]]
   [navdist.app.i18n :refer [tr]]
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

(defn config-panel
  []
  (let [state (<sub [:state-config-panel])]
    [:> mui/Dialog {:open (:open state)
                    :fullScreen true}
     [:> mui/AppBar {:style (merge s/grow s/drag-region)}
      [:> mui/Toolbar {:variant "dense"}
       [config-title]
       [config-control]]]]))
