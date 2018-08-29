(ns navdist-console.events-test
  (:require [navdist-console.events :as sut]
            [navdist-console.db :as ndb]
            [cljs.test :as t :include-macros true]))

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
          event [:update-db-edn {:file-db pr-test-db}]
          result (sut/update-db-from-edn cofx event)]
      (t/is (= (get-in result [:db :config]) test-db)))))

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
