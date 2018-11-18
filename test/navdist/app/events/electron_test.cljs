(ns navdist.app.events.electron-test
  (:require [navdist.app.events.electron :as sut]
            [cljs.test :as t :include-macros true]
            [taoensso.timbre :as timbre]))

;; related cofx
(defn cofx-current-window
  ([] {:current-window "current-window"})
  ([cofx] (assoc cofx :current-window "current-window")))

;; close-window
(t/deftest close-window
  (t/testing
      (let [cofx (-> {}
                     (cofx-current-window))
            r (sut/close-window cofx [:close-window])]
        (t/is (contains? r :shutdown-window))
        (t/is (= (:current-window (cofx-current-window))
                 (get-in r [:shutdown-window :current-window]))))))
