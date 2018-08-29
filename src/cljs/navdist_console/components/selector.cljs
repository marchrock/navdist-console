(ns navdist-console.components.selector
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.helper :refer [<sub >evt]]
   [navdist-console.i18n :as i18n]
   [taoensso.timbre :as timbre]
   ["material-ui" :as mui]))

(defn menu-item
  [item]
  [:> mui/MenuItem {:key (:key item) :value (:value item)}
   (:label item)])

(defn normal
  "Selector for all place."
  [material]
  (timbre/spy material)
  [:> mui/TextField {:style (:style material)
                     :select true
                     :value (<sub (:value-sub material))
                     :on-change #(>evt [(:change-event material) (-> % .-target .-value)])}
   (map #(menu-item %) (:items material))])
