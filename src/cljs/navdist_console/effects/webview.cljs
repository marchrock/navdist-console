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

;; inject debugger
(def api-endpoint-match #"^http://.*/kcsapi/.+$")

(defrecord ApiRecord [request-id response url headers])

;; queue for api response
(def api-queue (atom ()))

(defn debugger-message-handler
  [debugger e m p]
  (if (= m "Network.responseReceived")
    (when (and (= (-> p .-response .-mimeType) "text/plain")
               (re-find #"^http://.*/kcsapi/.+$" (-> p .-response .-url)))
      (timbre/debug "responseReceived")
      (let [headers (-> p .-response .-headers
                        (js->clj :keywordize-keys true))]
        (swap! api-queue conj (ApiRecord. (-> p .-requestId)
                                          (-> p .-response)
                                          (-> p .-response .-url)
                                          headers)))))
  (if (= m "Network.loadingFinished")
    (let [request-id (-> p .-requestId)
          record (-> (filter #(= request-id (:request-id %)) @api-queue)
                     first)]
      (when record
        (timbre/debug "loadingFinished")
        (.sendCommand debugger "Network.getResponseBody"
                      (clj->js {:requestId request-id})
                      (fn [e r]
                        (timbre/spy (:url record))
                        (timbre/spy (:headers record))
                        (timbre/spy (.-body r))))
        (swap! api-queue (fn [x] (remove #(= request-id (:request-id %)) x)))
        (timbre/spy @api-queue)))))

(defn-traced inject-debugger
  [req]
  (timbre/debug "inject-debugger")
  (let [webview (timbre/spy (:webview req))
        web-content (.getWebContents webview)
        debugger (.-debugger web-content)]
    (try
      (.attach debugger "1.2")
      (timbre/debug "debugger attached")
      (catch js/Error e
        (timbre/error e)))

    (.on debugger "detach" (fn [e r] (timbre/info "Debugger detached: " r)))
    (.on debugger "message" (fn [e m p] (debugger-message-handler debugger e m p)))
    (.sendCommand debugger "Network.enable")))

(defn-traced inject-webview-debugger
  [req]
  (let [webview (:target-webview req)]
    (.addEventListener webview "dom-ready"
                       (fn []
                         (inject-debugger {:webview webview}))
                       (clj->js {:once true}))))

(re-frame/reg-fx
 :webview-injectdebugger
 inject-webview-debugger)
