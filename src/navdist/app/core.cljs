(ns navdist.app.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   [navdist.app.events.core]
   [navdist.app.cofx.core]))

(defn pre-init-dispatcher
  []
  (timbre/debug "pre-init-dispatcher")
  (re-frame/dispatch-sync [:initialize-db]))

(defn post-init-dispatcher
  []
  (timbre/debug "post-init-dispatcher"))

(defn mount-root
  []
  (timbre/debug "mount-root")
  (re-frame/clear-subscription-cache!))

(defn reload-hook
  []
  (timbre/debug "reload-hook")
  (mount-root))

(defn ^:export init
  []
  (pre-init-dispatcher)
  (mount-root)
  (post-init-dispatcher))
