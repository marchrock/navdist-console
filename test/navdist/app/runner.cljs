(ns navdist.app.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [navdist.app.events.test]))

(doo-tests 'navdist.app.events.test)
