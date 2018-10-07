(ns navdist.app.events.electron
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]))

(defn-traced close-window
  [cofx _]
  (let [window (:current-window cofx)]
    {:shutdown-window {:current-window window}}))

(re-frame/reg-event-fx
 :close-window
 [(re-frame/inject-cofx :current-window)]
 close-window)
