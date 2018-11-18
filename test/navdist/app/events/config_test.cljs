(ns navdist.app.events.config-test
  (:require [navdist.app.events.config :as sut]
            [navdist.app.events.store :as store]
            [cljs.test :as t :include-macros true]))

;; config zoomfactor test
(defn cofx-current-window
  ([] {:curent-window "current-window"})
  ([cofx] (assoc cofx :current-window "current-window")))

(defn cofx-app-webview
  ([] {:app-webview "app-webview"})
  ([cofx] (assoc cofx :app-webview "app-webview")))

(t/deftest config-zoom-factor
  (t/testing "test config-zoomfactor"
    (let [db (store/initialize-db {} {})
          cofx (-> {:db db}
                   cofx-current-window
                   cofx-app-webview)
          f {:zoom-factor 100}
          v [:config-zoom-factor f]
          r (sut/config-zoom-factor cofx v)]
      (t/is (contains? r :db))
      (t/is (contains? r :resize-webview))
      (t/is (contains? r :resize-electron-window)))))
