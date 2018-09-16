(ns navdist.app.subs.config
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :config-locale
 (fn [db]
   (get-in db [:config :locale])))
