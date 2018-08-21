(ns navdist-console.main.db)

(defonce os (js/require "os"))

;; default db structure
(def default-db
  {:config
   {:uri "http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/"
    :fgc-css ".dmm-ntgnavi,.area-naviapp,.area-pickupgame,.area-menu,#ntg-recommend,#foot{display:none!important}body{margin:0;padding:0;overflow:hidden}#game_frame{position:fixed;left:50%;top:-16px;margin-left:-600px;z-index:1}.area-pickupgame,.area-menu{display:none!important}"
    :screenshot-path (str (.homedir os) "/Downloads/")
    :screenshot-prefix "navdist-"}
   :state
   {:notification {:open false :type :normal :message ""}
    :mute false
    :menu-drawer {:open false}}}
   )
