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

;; apply zoom factor to webview
(defn apply-zoom-factor-to-webview
  "Apply zoom factor to target webview.

  Some reasons for impl.
  - For zoom-factor 66, we actually need 2/3 to near the width 800.
  - Using divide instead of `/` is that it seems to confuse figwheel and lead to NPE."
  [webview zoom-factor]
  (cond (= zoom-factor 66) (.setZoomFactor webview 0.66666666)
        (number? zoom-factor) (.setZoomFactor webview (divide zoom-factor 100))
        :else (.setZoomFactor webview 1.0)))

(defn-traced apply-zoom-factor
  [req]
  (let [webview (:target-webview req)
        zoom-factor (:zoom-factor req)]
    (apply-zoom-factor-to-webview webview zoom-factor)))

(re-frame/reg-fx
 :apply-zoom-factor
 apply-zoom-factor)
