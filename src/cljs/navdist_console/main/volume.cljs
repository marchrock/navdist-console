(ns navdist-console.main.volume
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre]
   ))

(defn toggle-volume-handler
  "Toggle volume of target webview provided by :toggle-volume-on or :toggle-volume-off effect handler"
  [req]
  (let [mute (:mute req)
        target-webview (:target-webview req)]
    (.setAudioMuted target-webview mute)))

(defn-traced toggle-volume-effect
  [req]
  (timbre/spy req)
  (-> req
      toggle-volume-handler))

(re-frame/reg-fx
 :toggle-volume-on
 toggle-volume-effect)

(re-frame/reg-fx
 :toggle-volume-off
 toggle-volume-effect)

