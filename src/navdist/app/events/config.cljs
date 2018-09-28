(ns navdist.app.events.config
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

;; config zoom-factor
(defn-traced config-zoom-factor
  [cofx [_ v]]
  (let [db (:db cofx)
        zoom-factor (:zoom-factor v)
        updated-db (assoc-in db [:config :zoom-factor] zoom-factor)]
    {:db updated-db
     :resize-webview {:target-webview (:app-webview cofx)
                      :zoom-factor zoom-factor}
     :resize-electron-window {:current-window (:current-window cofx)
                              :zoom-factor zoom-factor}}))

(re-frame/reg-event-fx
 :config-zoom-factor
 [(re-frame/inject-cofx :current-window)
  (re-frame/inject-cofx :app-webview)]
 config-zoom-factor)

