(ns navdist-console.main.events
  (:require
   [re-frame.core :as re-frame]
   [navdist-console.main.db :as db]
   [navdist-console.main.subs :as subs]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [taoensso.timbre :as timbre :refer-macros [spy info]]
   [cljs-time.core :as time]
   [cljs-time.format :as time-format]
   ))

(defonce fs (js/require "fs"))

;; event cofx

(re-frame/reg-cofx
 :main-webview
 (fn-traced
  [cofx _]
  (assoc cofx :main-webview (.getElementById js/document "fgc-webview"))))

(re-frame/reg-cofx
 :screenshot-time-format
 (fn-traced
  [cofx _]
  (assoc cofx :screenshot-time-format (time-format/formatter "yyyyMMdd-hhmmss"))))

(re-frame/reg-cofx
 :now
 (fn-traced
  [cofx _]
  (assoc cofx :now (time/now))))

;; event handlers

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(re-frame/reg-event-fx
 ::initialize-webview
 [(re-frame/inject-cofx :main-webview)]
 (fn-traced
  [cofx _]
  (let [wv (:main-webview cofx)
        wv-css (re-frame/subscribe [::subs/fgc-webview-css])]
    (timbre/spy @wv-css)
    (.addEventListener wv "did-finish-load"
                       (fn [] (.insertCSS wv @wv-css))))))

(re-frame/reg-event-fx
 ::take-screenshot
 [(re-frame/inject-cofx :main-webview)
  (re-frame/inject-cofx :screenshot-time-format)
  (re-frame/inject-cofx :now)]
 (fn-traced
  [cofx _]
  (let [db (:db cofx)
        file-path (:screenshot-path db)
        file-prefix (:screenshot-prefix db)
        current-time (time-format/unparse (:screenshot-time-format cofx) (:now cofx))
        wv (:main-webview cofx)]
    (timbre/spy file-path)
    (timbre/spy file-prefix)
    (.capturePage wv
                  (fn [img]
                    (let [png-img (.toPNG img)
                          file-full-path (str file-path file-prefix current-time ".png")]
                      (.writeFile fs file-full-path png-img
                                  (fn [err]
                                    (if err
                                      (timbre/error err)
                                      (timbre/info "Screenshot Saved at" file-full-path)))))))
    )))
