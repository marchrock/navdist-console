(ns navdist-console.main.subs
  (:require
   [re-frame.core :as re-frame]
   ))

(re-frame/reg-sub
 ::fgc-uri
 (fn [db]
   (get-in db [:config :uri])))

(re-frame/reg-sub
 ::fgc-webview-css
 (fn [db]
   (get-in db [:config :fgc-css])))

(re-frame/reg-sub
 ::state-notification
 (fn [db]
   (get-in db [:state :notification])))
