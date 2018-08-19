(ns navdist-console.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [navdist-console.main.events :as events]
   [navdist-console.main.views :as views]
   [navdist-console.routes :as routes]
   [navdist-console.config :as config]
   ))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn initialize-dispatcher []
  (re-frame/dispatch [::events/initialize-webview]))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (initialize-dispatcher))
