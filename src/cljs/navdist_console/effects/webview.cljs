(ns navdist-console.effects.webview
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
            [taoensso.timbre :as timbre]))

(defn-traced webview-injectcss-handler
  [req]
  (let [webview (:target-webview req)
        user-css (:user-css req)]
    (.addEventListener webview "dom-ready"
                       (fn [] (.insertCSS webview user-css)))))

(re-frame/reg-fx
 :webview-injectcss
 webview-injectcss-handler)
