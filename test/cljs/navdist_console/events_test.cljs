(ns navdist-console.events-test
  (:require [navdist-console.events :as sut]
            [navdist-console.db :as ndb]
            [cljs.test :as t :include-macros true]
            [cljs-time.core :as time]))

(t/deftest initialize-db
  (t/testing "test initialize db"
    (let [db (sut/initialize-db {} [:initialize-db])]
      (t/is (= (:db db) ndb/default-db))))
  (t/testing "test when cofx has :os-home-dir"
    (let [db (-> {})
          cofx {:db db :os-home-dir "/home/clojure"}
          tdb (sut/initialize-db cofx nil)]
      (t/is (= (get-in (:db tdb) [:config :screenshot :path])
               "/home/clojure/Downloads/")))))

(t/deftest update-db-persist
  (t/testing "test updating db from user-data-dir"
    (let [user-data-dir "/home/clojure/.config/"
          db (sut/initialize-db {} [:initialize-db])
          cofx {:db db :user-data-dir user-data-dir}
          event [:update-db-persist]
          result (sut/update-db-persist cofx event)]
      (t/is (contains? result :read-edn))
      (t/is (= (get-in result [:read-edn :user-data-dir]) user-data-dir)))))

(t/deftest update-db-edn
  (t/testing "test updating db by loaded edn"
    (let [test-db {:test {:alpha "able" :bravo "baker"}}
          pr-test-db (pr-str test-db)
          db {:config {}}
          cofx {:db db}
          event [:initialize-last {:file-db pr-test-db}]
          result (sut/initialize-last cofx event)]
      (t/is (= (get-in result [:db :config]) test-db))))
  (t/testing "test electron window resize"
    (let [test-db {:zoom 0.75}
          pr-test-db (pr-str test-db)
          mock-current-app ["current-app"]
          cofx {:db {:config {}} :current-app mock-current-app}
          event [:initialize-last {:file-db pr-test-db}]
          result (sut/initialize-last cofx event)]
      (t/is (contains? result :resize-electron-window))
      (t/is (= (get-in result [:resize-electron-window :current-window]) mock-current-app)))))

(t/deftest initialize-webview
  (t/testing "test injecting css"
    (let [test-db (sut/initialize-db {} [:initialize-db])
          webview-mock ["webview"]
          cofx {:db test-db :main-webview webview-mock}
          event [:initialize-webview]
          result (sut/initialize-webview cofx event)]
      (t/is (contains? result :webview-injectcss))
      (t/is (= (get-in result [:webview-injectcss :target-webview]) webview-mock))
      (t/is (= (get-in result [:webview-injectcss :user-css])
               (get-in test-db [:config :user-css]))))))

(t/deftest close-app
  (t/testing "test close app"
    (let [db (sut/initialize-db {} [:initialize-db])
          mock-current-app ["current-app"]
          cofx {:db db :current-app mock-current-app}
          event [:close-app]
          result (sut/close-app cofx event)]
      (t/is (contains? result :shutdown-app))
      (t/is (= (get-in result [:shutdown-app :current-app]) mock-current-app)))))

(t/deftest take-screenshot
  (t/testing "test take screenshot"
    (let [db (sut/initialize-db {} [:initialize-db])
          current-time (time/now)
          mock-webview ["mock-webview"]
          cofx {:db db :now current-time :main-webview mock-webview}
          event [:take-screenshot]
          result (sut/take-screenshot cofx event)]
      (t/is (contains? result :webview-screenshot))
      (t/is (= (get-in result [:webview-screenshot :target-webview]) mock-webview))
      (t/is (= (get-in result [:webview-screenshot :time]) current-time)))))

(t/deftest toggle-volume-state
  (t/testing "test toggle volume state"
    (let [db (sut/initialize-db {} [:initialize-db])
          mock-webview ["mock-webview"]
          cofx {:db db :main-webview mock-webview}
          event [:toggle-volume-state]
          result (sut/toggle-volume-state cofx event)]
      (t/is (contains? result :toggle-volume))
      (t/is (contains? result :db))
      (t/is (= (get-in result [:toggle-volume :volume]) (not (get-in db [:state :volume]))))
      (t/is (= (get-in result [:toggle-volume :target-webview]) mock-webview))
      (t/is (= (get-in result [:db :state :volume]) (not (get-in db [:state :volume])))))))

(t/deftest do-reload
  (t/testing "test do reload of webview"
    (let [db (sut/initialize-db {} [:initialize-db])
          mock-webview ["mock-webview"]
          cofx {:db db :main-webview mock-webview}
          event [:do-reload]
          result (sut/do-reload cofx event)]
      (t/is (contains? result :webview-reload))
      (t/is (contains? result :db))
      (t/is (= (get-in result [:webview-reload :target-webview]) mock-webview))
      (t/is (= (get-in result [:db :state :app-bar :reload-enabled])
               (not (get-in db [:state :app-bar :reload-enabled])))))))
