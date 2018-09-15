(ns navdist.app.views.atoms.button
  (:require
   ["@material-ui/core" :as mui]))

(defn string-button
  [str-key & params]
  (let [p (first params)]
    [:> mui/Button p
     str-key]))
