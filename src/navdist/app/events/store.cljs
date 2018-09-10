(ns navdist.app.events.store
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]
   [navdist.app.db :as db]))

(defn-traced initialize-db
  [cofx _]
  (timbre/debug "initialize-db")
  (let [db (:db cofx)
        homedir (:os-home-dir cofx)]
    (timbre/spy homedir)
    {:db db/default-db}))

(re-frame/reg-event-fx
 :initialize-db
 [(re-frame/inject-cofx :os-home-dir)]
 initialize-db)
