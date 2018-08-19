(ns navdist-console.main.views
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.subs :as subs]
   ))

;; web-view

(defn fgc-webview []
  (let [uri (re-frame/subscribe [::subs/fgc-uri])]
    [:div {:class "fgc-div"}
     [:webview {:class "fgc-webview" :id "fgc-webview" :src @uri}]]))

(defn fgc-screenshot-button []
  [:button "Screen Shot"])

(defn fgc-mute-button []
  [:button "Mute"])

;; main

(defn main-panel []
  [:div
   [fgc-webview]
   [fgc-screenshot-button]
   [fgc-mute-button]])
