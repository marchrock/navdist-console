(ns navdist.app.events.state
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

(defn-traced toggle-webview-control-menu
  [db [_ v]]
  (let [anchor (:anchor v)
        open? (:open v)]
    (-> (assoc-in db [:state :app-bar :menu] {:open open? :anchor anchor})
        (timbre/spy))))

(re-frame/reg-event-db
 :toggle-webview-control-menu
 toggle-webview-control-menu)
