(ns navdist-console.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   [navdist-console.config :as config]
   [navdist-console.views :as views]
   [navdist-console.cofx]
   [navdist-console.events]
   [navdist-console.subs]
   [navdist-console.i18n]
   [navdist-console.effects.core]))

(defn dev-setup
  []
  (when config/debug?
    (enable-console-print!)
    (timbre/debug "dev mode")))

(defn initialize-dispatcher
  "Dispatch event which required after mouting root"
  []
  (re-frame/dispatch [:initialize-webview]))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/app-panel]
                  (.getElementById js/document "app")))

(defn reload-hook
  "Reload hook when figwheel hot-reload"
  []
  (timbre/info "reload-hook")
  (mount-root)
  (initialize-dispatcher))

(defn ^:export init
  "entry point"
  []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root)
  (initialize-dispatcher))
