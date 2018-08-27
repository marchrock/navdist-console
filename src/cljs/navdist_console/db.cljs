(ns navdist-console.db)

;;:uri "http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/"
;;:uri "https://www.youtube.com/"
;; default db structure
(def default-db
  {:config
   {:title "Navdist Console"
    :locale :ja
    :uri "http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/"
    :user-css ".dmm-ntgnavi,.area-naviapp,.area-pickupgame,.area-menu,#ntg-recommend,#foot{display:none!important}body{margin:0;padding:0;overflow:hidden}#game_frame{position:fixed;left:50%;top:-16px;margin-left:-600px;z-index:1}.area-pickupgame,.area-menu{display:none!important}"
    :screenshot {:path "~/Downloads/"
                 :prefix "navdist-"
                 :suffix ".png"
                 :time-format "yyyyMMdd-hhmmss"}}
   :state
   {:notification {:open false
                   :type :normal
                   :duration 1000
                   :message ""}
    :volume true
    :app-menu {:open false}
    :dialog {:shutdown false}
    :app-bar {:reload-enabled false}
    :settings {:open false}}}
   )
