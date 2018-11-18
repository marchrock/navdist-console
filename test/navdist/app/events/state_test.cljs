(ns navdist.app.events.state-test
  (:require [navdist.app.events.state :as sut]
            [navdist.app.events.store :as store]
            [cljs.test :as t :include-macros true]
            [taoensso.timbre :as timbre]))

;; helpers
(defn initial-db
  []
  (-> (store/initialize-db {} [:initialize-db])
      :db))

(t/deftest toggle-webview-control-menu
  (t/testing "testing webview control menu state"
    (let [db (initial-db)
          anchor-mock "anchor-mock"
          open-mock true
          r (sut/toggle-webview-control-menu
             db [:toggle-webview-control-menu
                 {:open open-mock :anchor anchor-mock}])]
      (t/is (= anchor-mock
               (get-in r [:state :app-bar :menu :anchor])))
      (t/is (= open-mock
               (get-in r [:state :app-bar :menu :open]))))))

(t/deftest toggle-dialog-confirm-shutdown
  (t/testing "testing dialog confirm shutdown state"
    (let [db (initial-db)
          open-mock true
          r (sut/toggle-dialog-confirm-shutdown
             db [:toggle-dialog-confirm-shutdown {:open open-mock}])]
      (t/is (= open-mock
               (get-in r [:state :dialog :shutdown]))))))

(t/deftest toggle-dialog-zoom-factor
  (t/testing "testing dialog zoom factor state"
    (let [db (initial-db)
          open-mock true
          r (sut/toggle-dialog-zoom-factor
             db [:toggle-dialog-zoom-factor {:open open-mock}])]
      (t/is (= open-mock
               (get-in r [:state :dialog :zoom-factor]))))))

(t/deftest toggle-notification
  (t/testing "testing notification state"
    (let [db (initial-db)
          open-mock true
          message-mock "message-mock"
          r (sut/toggle-notification
             db [:toggle-notification {:open open-mock :message message-mock}])]
      (t/is (= open-mock
               (get-in r [:state :notification :open])))
      (t/is (= message-mock
               (get-in r [:state :notification :message]))))))

(t/deftest toggle-config-panel
  (t/testing "testing toggle config panel state"
    (let [db (initial-db)
          open-mock true
          r (sut/toggle-config-panel
             db [:toggle-config-panel {:open open-mock}])]
      (t/is (= open-mock
               (get-in r [:state :config-panel :open]))))))
