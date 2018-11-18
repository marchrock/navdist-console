(ns navdist.app.events.config-test
  (:require [navdist.app.events.config :as sut]
            [navdist.app.events.store :as store]
            [cljs.test :as t :include-macros true]
            [taoensso.timbre :as timbre]))

;; related cofx
(defn cofx-current-window
  ([] {:current-window "current-window"})
  ([cofx] (assoc cofx :current-window "current-window")))

(defn cofx-app-webview
  ([] {:app-webview "app-webview"})
  ([cofx] (assoc cofx :app-webview "app-webview")))

(defn cofx-screenshot-directory
  ([] {:screenshot-directory "screenshot-dir"})
  ([cofx] (assoc cofx :screenshot-directory "screenshot-dir")))

;; config zoomfactor test
(t/deftest config-zoom-factor
  (t/testing "test config-zoomfactor"
    (let [db (store/initialize-db {} {})
          cofx (-> db
                   cofx-current-window
                   cofx-app-webview)
          mock {:zoom-factor 42}
          v [:config-zoom-factor mock]
          r (sut/config-zoom-factor cofx v)]
      (t/is (contains? r :db))
      (t/is (contains? r :resize-webview))
      (t/is (contains? r :resize-electron-window))
      (t/is (= (:zoom-factor mock)
               (get-in r [:db :config :zoom-factor])))
      (t/is (= (:zoom-factor mock)
               (get-in r [:resize-webview :zoom-factor])))
      (t/is (= (:zoom-factor mock)
               (get-in r [:resize-electron-window :zoom-factor])))
      (t/is (= (:app-webview (cofx-app-webview))
               (get-in r [:resize-webview :target-webview])))
      (t/is (= (:current-window (cofx-current-window))
               (get-in r [:resize-electron-window :current-window]))))))

;; config locale test
(t/deftest config-locale
  (t/testing "test config-locale"
    (let [db (store/initialize-db {} {})
          mock {:value :la}
          r (sut/config-locale (:db db) [:config-locale mock])]
      (t/is (= (get-in r [:config :locale]) (:value mock))))))

;; config screenshot directory test
(t/deftest config-screenshot-directory
  (t/testing "test screenshot dir"
    (let [db (store/initialize-db {} {})
          cofx (-> db
                   cofx-screenshot-directory)
          r (sut/config-screenshot-directory cofx [:config-screenshot-directory])]
      (t/is (= (:screenshot-directory (cofx-screenshot-directory))
               (get-in r [:db :config :screenshot :path]))))))
