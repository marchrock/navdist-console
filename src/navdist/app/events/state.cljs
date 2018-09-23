(ns navdist.app.events.state
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.tracing :refer-macros [defn-traced]]
   [taoensso.timbre :as timbre]))

;; toggle open state webview control menu
(defn-traced toggle-webview-control-menu
  [db [_ v]]
  (let [anchor (:anchor v)
        open? (:open v)]
    (-> (assoc-in db [:state :app-bar :menu] {:open open? :anchor anchor})
        (timbre/spy))))

(re-frame/reg-event-db
 :toggle-webview-control-menu
 toggle-webview-control-menu)

;; toggle open state of dialog confirm shutdown
(defn-traced toggle-dialog-confirm-shutdown
  [db [_ v]]
  (let [open? (:open v)]
    (-> (assoc-in db [:state :dialog :shutdown] open?)
        (timbre/spy))))

(re-frame/reg-event-db
 :toggle-dialog-confirm-shutdown
 toggle-dialog-confirm-shutdown)

;; toggle open state of dialog to choose zoom factor
(defn-traced toggle-dialog-zoom-factor
  [db [_ v]]
  (let [open? (:open v)]
    (-> (assoc-in db [:state :dialog :zoom-factor] open?)
        (timbre/spy))))

(re-frame/reg-event-db
 :toggle-dialog-zoom-factor
 toggle-dialog-zoom-factor)

;;
(defn-traced toggle-notification
  [db [_ v]]
  (let [open? (:open v)
        message (:message v)]
    (-> (assoc-in db [:state :notification] {:open open? :message message})
        (timbre/spy))))

(re-frame/reg-event-db
 :toggle-notification
 toggle-notification)
