(ns navdist-console.i18n
  (:require
   [re-frame.core :as re-frame]
   [taoensso.tempura :as tempura :refer [tr]]
   [taoensso.timbre :as timbre]
   ))

(def app-name
  "Navdist Console")

(def navdist-locale-list
  [:ja :en])

(def navdist-i18n-dictionary
  {:ja {:locale-name "日本語"
        :app-menu {:shutdown "終了"
                   :settings "設定"}
        :settings {:locale "言語設定"}
        :dialog {:shutdown (str app-name " を終了しますか？")}
        :button {:apply "適用"
                 :cancel "キャンセル"
                 :shutdown "終了"}
        }

   :en {:locale-name "English"
        :app-menu {:shutdown "Shutdown"
                   :settings "Settings"}
        :settings {:locale "Locale"}
        :dialog {:shutdown (str "Shutdown " app-name " ?")}
        :button {:apply "Apply"
                 :cancel "Cancel"
                 :shutdown "Shutdown"}
        :screenshot {:success "Screenshot saved!"
                     :failure "Screenshot failed."}
        }})

;; option for tempura
(def opts
  {:dict navdist-i18n-dictionary})

(defn tr-nd
  [key]
  (timbre/spy key)
  (let [locale (re-frame/subscribe [:config-locale])]
    (timbre/spy (tr opts [@locale :en] key))))

(defn tr-lc
  [key locale]
  (timbre/spy (tr opts [locale] key)))
