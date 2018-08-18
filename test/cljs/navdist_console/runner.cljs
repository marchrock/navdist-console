(ns navdist-console.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [navdist-console.core-test]))

(doo-tests 'navdist-console.core-test)
