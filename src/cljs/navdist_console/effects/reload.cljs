(ns navdist-console.effects.reload
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]))

(defn do-webview-reload
  [req]
  (let [wv (:target-webview req)]
    (.reloadIgnoringCache wv)))

(defn-traced webview-reload-handler
  [req]
  (-> req
      do-webview-reload))

(re-frame/reg-fx
 :webview-reload
 webview-reload-handler)
