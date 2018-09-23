(ns navdist.app.views.atoms.selector
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]))

(defn menu-item
  [item]
  [:> mui/MenuItem {:key (:key item) :value (:value item)}
   (:label item)])

(def selector-text-field-param
  {:select true})

(defn normal
  "Selector for all place."
  [val items & params]
  (let [custom-param (first params)
        p (merge selector-text-field-param
                 custom-param
                 {:value @val
                  :on-change #(let [v (-> % .-target .-value (timbre/spy))]
                                (reset! val v))})]
    (timbre/spy p)
    (timbre/spy items)
    [:> mui/TextField p
     (map #(menu-item {:key (:factor %) :value (:factor %) :label (:label %)}) items)]))
