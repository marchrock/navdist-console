(ns navdist-console.main.subs
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]
   ))

(re-frame/reg-sub
 ::fgc-uri
 (fn [db]
   (get-in db [:config :uri])))

(re-frame/reg-sub
 ::app-locale
 (fn [db]
   (timbre/spy db)
   (get-in db [:config :locale])))

(re-frame/reg-sub
 ::fgc-webview-css
 (fn [db]
   (get-in db [:config :fgc-css])))

(re-frame/reg-sub
 ::state-notification
 (fn [db]
   (get-in db [:state :notification])))

(re-frame/reg-sub
 ::state-mute
 (fn [db]
   (get-in db [:state :mute])))

(re-frame/reg-sub
 ::state-menu-drawer
 (fn [db]
   (get-in db [:state :menu-drawer])))

(re-frame/reg-sub
 ::state-dialog-shutdown
 (fn [db]
   (get-in db [:state :dialog :shutdown])))

