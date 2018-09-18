(ns navdist.app.events.webview
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]))

(defn-traced webview-screenshot
  [cofx [_ v]]
  (let [ss-config (-> (:db cofx) (get-in [:config :screenshot]))]
    {:webview-save-screenshot
     {:config ss-config
      :target-webview (:app-webview cofx)
      :time (:now cofx)}}))

(re-frame/reg-event-fx
 :webview-screenshot
 [(re-frame/inject-cofx :app-webview)
  (re-frame/inject-cofx :now)]
 webview-screenshot)
