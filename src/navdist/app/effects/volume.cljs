(ns navdist.app.effects.volume
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

(defn-traced webview-toggle-volume
  [req]
  (timbre/spy req)
  (let [volume (:target-volume req)
        webview (:target-webview req)]
    (.setAudioMuted webview (not volume))))

(re-frame/reg-fx
 :webview-toggle-volume
 webview-toggle-volume)
