(ns navdist.app.subs.config
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :config-locale
 (fn [db]
   (get-in db [:config :locale])))

(re-frame/reg-sub
 :config-uri
 (fn [db]
   (get-in db [:config :uri])))

(re-frame/reg-sub
 :config-zoom-factor
 (fn [db]
   (get-in db [:config :zoom-factor])))

(re-frame/reg-sub
 :config-screenshot
 (fn [db]
   (get-in db [:config :screenshot])))
