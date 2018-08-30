(ns navdist-console.effects.persist
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]
   [cljs.reader :as reader]))

(def fs (js/require "fs"))

(defonce settings-file-name "settings.edn")

(defn write-to-file
  [req]
  (let [full-file-path (str (:user-data-dir req) "/" settings-file-name)]
    (.writeFile fs full-file-path (:pr-config req) (fn [err]
                                                     (if err
                                                       (timbre/error err))))))

(defn read-from-file
  [req]
  (let [full-file-path (str (:user-data-dir req) "/" settings-file-name)]
    (.readFile fs full-file-path
               (fn [err data]
                 (if err
                   (timbre/error err)
                   (re-frame/dispatch [:initialize-last {:file-db data}]))))))

(defn parse-config-filter
  [req]
  (-> req
      :config
      pr-str
      (as-> x (assoc-in req [:pr-config] x))))

(defn-traced write-edn-handler
  [req]
  (-> req
      parse-config-filter
      write-to-file))

(re-frame/reg-fx
 :write-edn
 write-edn-handler)

(defn-traced read-edn-handler
  [req]
  (-> req
      read-from-file))

(re-frame/reg-fx
 :read-edn
 read-edn-handler)
