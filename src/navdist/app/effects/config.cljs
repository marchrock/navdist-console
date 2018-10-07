(ns navdist.app.effects.config
  (:require
   [navdist.app.helper :refer [>evt]]
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]
   [cljs.reader :as reader]
   ["fs" :as fs]))

(defonce config-file-name "config.edn")

(defn write
  [req]
  (let [file-path (:file-path req)
        config (:pr-config req)]
    (.writeFile fs file-path config
                (fn [err]
                  (if err
                    (timbre/error err))))))

(defn read
  [req]
  (let [file-path (:file-path req)]
    (.readFile fs file-path
               (fn [err data]
                 (if err
                   (timbre/error err)
                   (>evt [:post-initialize-db {:file-db data}]))))))

(defn file-path-filter
  "Construct file path of config file"
  [req]
  (-> req
      :user-data-dir
      (str "/" config-file-name)
      (as-> x (assoc-in req [:file-path] x))))

(defn parse-config-filter
  "Parse config to writable format using pr-str"
  [req]
  (-> req
      :config
      pr-str
      (as-> x (assoc-in req [:pr-config] x))))

(defn-traced edn-write-handler
  [req]
  (-> req
      parse-config-filter
      file-path-filter
      write))

(defn-traced edn-read-handler
  [req]
  (-> req
      file-path-filter
      read))

(re-frame/reg-fx
 :edn-write
 edn-write-handler)

(re-frame/reg-fx
 :edn-read
 edn-read-handler)
