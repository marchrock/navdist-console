(ns navdist.app.i18n
  (:require
   [navdist.app.db :as db]
   [navdist.app.helper :refer [<sub]]
   [re-frame.core :as re-frame]
   [taoensso.tempura :as tempura]
   [taoensso.timbre :as timbre]))

(def navdist-locale-list
  [:en])

(def navdist-i18n-en-dict
  {:en {:locale-name "English"
        :app-bar-menu {:shutdown "Shutdown"
                       :settings "Settings"
                       :reload "Reload"
                       :zoom "Zoom"}
        :screenshot {:success "Screenshot saved!"
                     :failure "Screenshot failed"}
        :dialog {:shutdown (str "Shutdown " db/app-name "?")
                 :zoom-factor "Select zoom factor"}
        :button {:apply "Apply"
                 :cancel "Cancel"
                 :shutdown "Shutdown"
                 :change "Change"
                 :save "Save"}
        :config {:title "Settings"}}})

(def navdist-i18n-dict
  (merge navdist-i18n-en-dict))

;; option for tempura
(def tr-opts
  {:dict navdist-i18n-dict
   :default-locale :en})

(defn tr
  ([key]
   (let [locale (<sub [:config-locale])]
     (tr key locale)))
  ([key locale]
   (timbre/spy (tempura/tr tr-opts [locale] key))))
