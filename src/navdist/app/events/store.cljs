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
        home-dir (:os-home-dir cofx)]
    (timbre/spy home-dir)
    (if (nil? home-dir)
      {:db db/default-db}
      {:db (assoc-in db/default-db [:config :screenshot :path] (str home-dir "/Downloads"))})))

(re-frame/reg-event-fx
 :initialize-db
 [(re-frame/inject-cofx :os-home-dir)]
 initialize-db)

;; read or write config to edn
(defn-traced config-persist
  [cofx [_ v]]
  (let [type (:type v)
        db (:db cofx)
        user-data-dir (:user-data-dir cofx)]
    (cond (= type :read) {:edn-read {:user-data-dir user-data-dir}}
          (= type :write) {:edn-write {:config (:config db)
                                       :user-data-dir user-data-dir}})))

(re-frame/reg-event-fx
 :config-persist
 [(re-frame/inject-cofx :user-data-dir)]
 config-persist)
