(ns navdist.app.effects.screenshot
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [cljs-time.format :as time-format]
   [taoensso.timbre :as timbre]
   [navdist.app.helper :refer [>evt]]
   ["fs" :as fs]))

(defn save-screenshot-to-file
  [img file-path]
  (let [png-img (.toPNG img)]
    (.writeFile fs file-path png-img
                (fn [err]
                  (if err
                    (do (timbre/error err)
                        (>evt [:toggle-notification {:open true
                                                     :message [:screenshot/failure]}]))
                    (>evt [:toggle-notification {:open true
                                                 :message [:screenshot/success]}]))))))

(defn save-screenshot
  [req]
  (let [file-path (:full-file-path req)
        webview (:target-webview req)]
    (.capturePage webview #(save-screenshot-to-file % file-path))))

(defn path-delimiter-check
  [path]
  (-> path
      (as-> x (if (= (last x) "/")
                x
                (str x "/")))
      (timbre/spy)))

(defn construct-file-path
  [req]
  (timbre/spy req)
  (let [config (:config req)
        format (time-format/formatter-local (:time-format config))
        ss-time (time-format/unparse-local format (:time req))]
    (-> (:path config)
        (path-delimiter-check)
        (str (:prefix config) ss-time (:suffix config))
        (as-> x (assoc req :full-file-path x))
        (timbre/spy))))

(defn-traced webview-save-screenshot
  [req]
  (-> req
      construct-file-path
      save-screenshot))

(re-frame/reg-fx
 :webview-save-screenshot
 webview-save-screenshot)
