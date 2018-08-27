(ns navdist-console.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [navdist-console.events-test]))

(doo-tests 'navdist-console.events-test)
