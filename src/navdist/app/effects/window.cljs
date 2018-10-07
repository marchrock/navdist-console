(ns navdist.app.effects.window
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

(def app-bar-height 48)

(def window-size-by-zoom-factor
  {100 {:width 1200 :height 720}
   75  {:width 900 :height 540}
   66  {:width 800 :height 480}})

(defn-traced resize-electron-window
  [req]
  (let [window (:current-window req)
        zoom-factor (:zoom-factor req)
        size (get-in window-size-by-zoom-factor [zoom-factor])]
    (timbre/spy size)
    (if (some? size)
      (.setSize window (:width size) (+ (:height size) app-bar-height)))))

(re-frame/reg-fx
 :resize-electron-window
 resize-electron-window)
