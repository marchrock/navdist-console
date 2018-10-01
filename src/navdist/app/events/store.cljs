(ns navdist.app.events.store
  (:require
   [cljs.reader :as reader]
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

(defn-traced post-initialize-db
  [cofx [_ v]]
  (timbre/debug "update-db")
  (let [db (:db cofx)
        updated-db (-> (:file-db v)
                       str
                       reader/read-string
                       (as-> x (update-in db [:config] conj x))
                       timbre/spy)]
    {:db updated-db
     :resize-electron-window {:current-window (:current-window cofx)
                              :zoom-factor (get-in updated-db [:config :zoom-factor])}}))

(re-frame/reg-event-fx
 :post-initialize-db
 [(re-frame/inject-cofx :current-window)]
 post-initialize-db)

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
