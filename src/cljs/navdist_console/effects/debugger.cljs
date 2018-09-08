(ns navdist-console.effects.debugger
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
            [navdist-console.records :refer [ReceivedApi]]
            [taoensso.timbre :as timbre]))

;; inject debugger
(def api-endpoint-match #"^http://.*/kcsapi/.+$")

;; queue for api response
(def api-queue (atom ()))

(defn network-response-received-filter
  [req]
  (when (= (:method req) "Network.responseReceived")
    (when (and (= (-> (:params req) .-response .-mimeType) "text/plain")
               (re-find api-endpoint-match (-> (:params req) .-response .-url)))
      (timbre/debug "responseReceived")
      (let [p (:params req)
            headers (-> p .-response .-headers (js->clj :keywordize-keys true))]
        (swap! api-queue conj (ReceivedApi. (-> p .-requestId)
                                            (-> p .-response)
                                            (-> p .-response .-url)
                                            headers)))))
  req)

(defn network-loading-finished-filter
  [req]
  (when (= (:method req) "Network.loadingFinished")
    (let [request-id (-> (:params req) .-requestId)
          record (-> (filter #(= request-id (:request-id %)) @api-queue)
                     first)]
      (when record
        (timbre/debug "loadingFinished")
        (.sendCommand (:debugger req) "Network.getResponseBody"
                      (clj->js {:requestId request-id})
                      (fn [e r]
                        (timbre/spy (:url record))
                        (timbre/spy (:headers record))
                        (timbre/spy (.-body r))))
        (swap! api-queue (fn [x] (remove #(= request-id (:request-id %)) x))))))
  req)

(defn debugger-message-handler
  [debugger e m p]
  (let [req {:debugger debugger
             :event e
             :method m
             :params p}]
    (-> req
        network-response-received-filter
        network-loading-finished-filter)))

(defn-traced inject-debugger
  [req]
  (timbre/debug "inject-debugger")
  (let [debugger (-> (:webview req) .getWebContents .-debugger)]
    (try
      (.attach debugger "1.2")
      (timbre/debug "debugger attached")

      (.on debugger "detach" (fn [e r] (timbre/info "Debugger detached: " r)))
      ;; "message" event handler for debugger handles real things
      (.on debugger "message" (fn [e m p] (debugger-message-handler debugger e m p)))
      (.sendCommand debugger "Network.enable")
      (timbre/debug "debugger successfully initialized")

      (catch js/Error e
        (timbre/error e)))))

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
