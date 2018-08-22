(ns navdist-console.i18n
  (:require
   [re-frame.core :as re-frame]
   [taoensso.tempura :as tempura :refer [tr]]
   [taoensso.timbre :as timbre]
   [navdist-console.main.subs :as subs]
   ))

(def app-name
  "Navdist Console")

(def navdist-i18n-dictionary
  {:ja {:global-menu
        {:shutdown "終了"}
        :dialog
        {:shutdown (str app-name " を終了しますか？")}
        :button
        {:apply "適用"
         :cancel "キャンセル"
         :shutdown "終了"}
        }

   :en {:global-menu
        {:shutdown "Shutdown"}
        :dialog
        {:shutdown (str "Shutdown " app-name " ?")}
        :button
        {:apply "Apply"
         :cancel "Cancel"
         :shutdown "Shutdown"}
        }})

;; option for tempura
(def opts
  {:dict navdist-i18n-dictionary})

(defn tr-nd
  [key]
  (let [locale (re-frame/subscribe [::subs/app-locale])]
    (timbre/spy (tr opts [@locale :en] key))))
