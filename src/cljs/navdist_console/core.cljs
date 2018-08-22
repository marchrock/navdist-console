(ns navdist-console.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre :refer-macros [info]]
   [navdist-console.main.events :as events]
   [navdist-console.main.views :as views]
   [navdist-console.routes :as routes]
   [navdist-console.config :as config]
   [navdist-console.i18n]
   ))

(defn dev-setup
  []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn initialize-dispatcher
  "Dispatch event which required after mouting root"
  []
  (re-frame/dispatch [::events/initialize-webview]))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
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
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (initialize-dispatcher))
