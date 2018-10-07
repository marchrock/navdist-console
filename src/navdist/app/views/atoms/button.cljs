(ns navdist.app.views.atoms.button
  (:require
   [navdist.app.i18n :refer [tr]]
   [taoensso.timbre :as timbre]
   ["@material-ui/core" :as mui]))

(defn string-button
  [str-key & params]
  (timbre/spy str-key)
  (timbre/spy (string? str-key))
  (let [p (first params)]
    [:> mui/Button p
     (if (string? str-key)
       str-key
       (tr str-key))]))
