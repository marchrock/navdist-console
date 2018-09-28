(ns navdist.app.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   [navdist.app.views :as views]
   [navdist.app.events.core]
   [navdist.app.cofx.core]
   [navdist.app.subs.core]
   [navdist.app.effects.core]))

(defn pre-init-dispatcher
  []
  (timbre/debug "pre-init-dispatcher")
  (re-frame/dispatch-sync [:initialize-db]))

(defn post-init-dispatcher
  []
  (timbre/debug "post-init-dispatcher")
  (re-frame/dispatch-sync [:config-persist {:type :read}]))

(defn mount-root
  []
  (timbre/debug "mount-root")
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/app-view]
                  (.getElementById js/document "app")))

(defn reload-hook
  []
  (timbre/debug "reload-hook")
  (mount-root))

(defn ^:export init
  []
  (pre-init-dispatcher)
  (mount-root)
  (post-init-dispatcher))
