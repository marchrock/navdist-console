(ns navdist-console.components.button
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.i18n :as i18n]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]
   ["material-ui-icons" :as mui-icons]))

(def menu-button-style
  (clj->js {:marginLeft -18 :marginRight 10}))

;; icon buttons
(defn app-menu
  [active?]
  [:> mui/IconButton {:className "no-drag-region"
                      :on-click #(>evt [:toggle-app-menu true])
                      :style menu-button-style
                      :color "inherit"}
   [:> mui-icons/Menu]])

(defn screenshot
  []
  [:> mui/IconButton {:on-click #(>evt [:take-screenshot])
                      :color "inherit"}
   [:> mui-icons/CameraAlt]])

(defn toggle-volume
  []
  [:> mui/IconButton {:on-click #(>evt [:toggle-volume-state])
                      :color "inherit"}
   (if (timbre/spy (<sub [:state-volume]))
     [:> mui-icons/VolumeUp]
     [:> mui-icons/VolumeOff])])

(defn reload
  [disabled?]
  [:> mui/IconButton {:on-click #(>evt [:do-reload])
                      :color "inherit"
                      :disabled disabled?}
   [:> mui-icons/Autorenew]])

;; string buttons
(defn str-button
  [string-key event-key & button-attr]
  [:> mui/Button (merge {:on-click #(>evt event-key)} (first button-attr))
   (i18n/tr-nd [string-key])])

;; switch
(defn switch-button
  [checked? on-change-event]
  [:> mui/Switch {:checked checked?
                  :on-change #(>evt on-change-event)
                  :color "inherit"}])
