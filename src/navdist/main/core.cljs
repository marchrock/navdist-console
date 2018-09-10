(ns navdist.main.core
  (:require
   ["electron" :refer [app BrowserWindow]]))

(def main-window (atom nil))

(def app-name "Navdist Console")

(defn init-electron
  []
  (reset! main-window (BrowserWindow.
                       (clj->js {:width 1200
                                 :height 768
                                 :useContentsSize true
                                 :frame false
                                 :title app-name})))

  (.loadURL @main-window (str "file://" js/__dirname "/public/index.html"))

  (.on @main-window "closed" #(reset! main-window nil)))

(defn main
  []
  (.on app "window-all-closed" #(.quit app))
  (.on app "ready" init-electron))
