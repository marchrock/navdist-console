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
