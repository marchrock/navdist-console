(ns navdist-console.main.subs
  (:require
   [re-frame.core :as re-frame]
   ))

(re-frame/reg-sub
 ::fgc-uri
 (fn [db]
   (:uri db)))

(re-frame/reg-sub
 ::fgc-webview-css
 (fn [db]
   (:fgc-css db)))
