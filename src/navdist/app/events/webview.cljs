(ns navdist.app.events.webview
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

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

(defn-traced webview-volume
  [cofx [_ v]]
  (let [db (:db cofx)
        current-volume (get-in db [:state :volume])
        updated-db (assoc-in db [:state :volume] (not current-volume))]
    (timbre/spy updated-db)
    {:webview-toggle-volume {:target-webview (:app-webview cofx)
                             :target-volume (not current-volume)}
     :db updated-db}))

(re-frame/reg-event-fx
 :webview-volume
 [(re-frame/inject-cofx :app-webview)]
 webview-volume)
