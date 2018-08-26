(ns navdist-console.effects.screenshot
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [cljs-time.format :as time-format]
   [taoensso.timbre :as timbre]))

(defonce fs (js/require "fs"))
(defonce electron-dialog (-> (js/require "electron") .-remote .-dialog))

(defn save-screenshot-to-file
  [img full-file-path on-success on-failure]
  (let [png-img (.toPNG img)]
    (.writeFile fs full-file-path png-img
                (fn [err]
                  (if err
                    (do (timbre/error err)
                        (re-frame/dispatch [on-failure {:msg [:screenshot/failure]}]))
                    (re-frame/dispatch [on-success {:msg [:screenshot/success]}]))))))

(defn take-screenshot-from-webview
  "Take screenshot of target webview provided by :webview-screenshot effect handler"
  [req]
  (let [full-file-path (:full-file-path req)
        target-webview (:target-webview req)
        on-success (first (:on-success req))
        on-failure (first (:on-failure req))]
    (.capturePage target-webview #(save-screenshot-to-file % full-file-path on-success on-failure))))

(defn path-delimiter-check
  [path]
  (-> path
      (as-> x (if (= (last x) "/")
                x
                (str x "/")))
      (timbre/spy)))

(defn create-file-path
  "Create file path of screenshot with provided info"
  [req]
  (timbre/spy req)
  (let [config (:config req)
        format (time-format/formatter (:time-format config))
        ss-time (time-format/unparse format (:time req))
        formatted-path (path-delimiter-check (:path config))]
    (-> (str formatted-path (:prefix config) ss-time (:suffix config))
        (as-> x (assoc req :full-file-path x))
        (timbre/spy))))

(defn-traced screenshot-handler
  [req]
  (timbre/spy req)
  (-> req
      create-file-path
      take-screenshot-from-webview))

(re-frame/reg-fx
 :webview-screenshot
 screenshot-handler)

(defn screenshot-directory-selector
  []
  (.showOpenDialog electron-dialog nil (clj->js {:properties ["openDirectory"]})))

(defn-traced screenshot-path-handler
  [req]
  (timbre/spy req)
  (let [file-path (screenshot-directory-selector)]
    (when (some? file-path)
      (re-frame/dispatch [:settings-screenshot-path {:path (first file-path)}]))))

(re-frame/reg-fx
 :screenshot-path-dialog
 screenshot-path-handler)
