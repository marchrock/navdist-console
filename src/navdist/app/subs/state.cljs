(ns navdist.app.subs.state
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :state-volume
 (fn [db]
   (get-in db [:state :volume])))

(re-frame/reg-sub
 :state-app-bar-menu
 (fn [db]
   (get-in db [:state :app-bar :menu])))

(re-frame/reg-sub
 :state-dialog-shutdown
 (fn [db]
   (get-in db [:state :dialog :shutdown])))

(re-frame/reg-sub
 :state-notification
 (fn [db]
   (get-in db [:state :notification])))
