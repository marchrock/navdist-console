(ns navdist-console.subs
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as timbre]))

;; config subs
(re-frame/reg-sub
 :config-uri
 (fn [db]
   (get-in db [:config :uri])))

(re-frame/reg-sub
 :config-title
 (fn [db]
   (get-in db [:config :title])))

(re-frame/reg-sub
 :config-locale
 (fn [db]
   (get-in db [:config :locale])))

;; state subs
(re-frame/reg-sub
 :state-volume
 (fn [db]
   (get-in db [:state :volume])))

(re-frame/reg-sub
 :state-app-menu
 (fn [db]
   (get-in db [:state :app-menu])))

(re-frame/reg-sub
 :state-dialog-shutdown
 (fn [db]
   (get-in db [:state :dialog :shutdown])))

(re-frame/reg-sub
 :state-app-notification
 (fn [db]
   (get-in db [:state :notification])))

(re-frame/reg-sub
 :state-app-bar
 (fn [db]
   (get-in db [:state :app-bar])))
