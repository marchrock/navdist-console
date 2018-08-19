(ns navdist-console.main.events
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.db :as db]
   [navdist-console.main.subs :as subs]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre :refer-macros [spy info]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(re-frame/reg-event-fx
 ::initialize-webview
 (fn-traced
  [_ _]
  (let [wv (.getElementById js/document "fgc-webview")
        wv-css (re-frame/subscribe [::subs/fgc-webview-css])]
    (timbre/spy @wv-css)
    (.addEventListener wv "dom-ready"
                       (fn [] (.insertCSS wv @wv-css))))))
