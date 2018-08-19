(ns navdist-console.main.screenshot
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]
   ))

(defonce fs (js/require "fs"))

(defn screenshot-handler
  [req]
  (let [full-file-path (:full-file-path req)
        target-webview (:target-webview req)
        on-success (first (:on-success req))
        on-failure (first (:on-failure req))]
    (timbre/spy on-success)
    (.capturePage target-webview
                  (fn [img]
                    (let [png-img (.toPNG img)]
                      (.writeFile fs full-file-path png-img
                                  (fn [err]
                                    (if err
                                      (re-frame/dispatch [on-failure {:err err}])
                                      (re-frame/dispatch [on-success {:msg full-file-path}])))))))))

(defn-traced screenshot-effect
  [req]
  (timbre/spy req)
  (-> req
      screenshot-handler))

(re-frame/reg-fx
 :webview-screenshot
 screenshot-effect)
