(ns navdist-console.effects.volume
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]))

(defn toggle-volume-handler
  "Toggle volume of target webview"
  [req]
  (let [volume (:volume req)
        target-webview (:target-webview req)]
    (.setAudioMuted target-webview (not volume))))

(defn-traced toggle-volume-effect
  [req]
  (timbre/spy req)
  (-> req
      toggle-volume-handler))

(re-frame/reg-fx
 :toggle-volume
 toggle-volume-effect)
