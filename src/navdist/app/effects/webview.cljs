(ns navdist.app.effects.webview
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

(defn-traced resize-webview
  "Apply zoom factor to target webview.

  Some reasons for impl.
  - For zoom-factor 66, we actually need 2/3 to near the width 800.
  - Using divide instead of `/` is that it seems to confuse figwheel and lead to NPE."
  [req]
  (let [webview (:target-webview req)
        zoom-factor (:zoom-factor req)]
    (cond (= zoom-factor 66) (.setZoomFactor webview 0.66666666)
          (number? zoom-factor) (.setZoomFactor webview (divide zoom-factor 100))
          :else (.setZoomFactor webview 1.0))))

(re-frame/reg-fx
 :resize-webview
 resize-webview)
