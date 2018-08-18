(ns navdist-console.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [navdist-console.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
