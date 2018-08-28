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
        :settings {:locale "言語設定"
                   :screenshot-path "スクリーンショット保存フォルダ"}
        :dialog {:shutdown (str app-name " を終了しますか？")}
        :button {:apply "適用"
                 :cancel "キャンセル"
                 :shutdown "終了"
                 :change "変更"}
        :screenshot {:success "スクリーンショットを保存しました"
                     :failure "スクリーンショットの保存に失敗しました"}
        }

   :en {:locale-name "English"
        :app-menu {:shutdown "Shutdown"
                   :settings "Settings"}
        :settings {:locale "Locale"
                   :screenshot-path "Screenshot Folder"}
        :dialog {:shutdown (str "Shutdown " app-name " ?")}
        :button {:apply "Apply"
                 :cancel "Cancel"
                 :shutdown "Shutdown"
                 :change "Change"}
        :screenshot {:success "Screenshot saved!"
                     :failure "Screenshot failed."}
        }})

;; option for tempura
(def opts
  {:dict navdist-i18n-dictionary
   :default-locale :en})

(defn tr-nd
  [key]
  (timbre/spy key)
  (let [locale (re-frame/subscribe [:config-locale])]
    (timbre/spy (tr opts [@locale] key))))

(defn tr-lc
  [key locale]
  (timbre/spy (tr opts [locale] key)))
