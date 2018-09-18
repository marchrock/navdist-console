(ns navdist.app.db)

(def app-name
  "Navdist Console")

;;:uri "http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/"
;;:uri "https://www.youtube.com/"
(def default-db
  {:config
   {:locale :en
    :uri "https://www.youtube.com/"
    :screenshot {:path "~/Downloads/"
                 :prefix "navdist-"
                 :suffix ".png"
                 :time-format "yyyyMMdd-HHmmssZZ"}}
   :state
   {:app-bar {:menu {:open false :target nil}}
    :volume true
    :dialog {:shutdown false}}
   :api {}})

(def user-css
  ".dmm-ntgnavi,.area-naviapp,.area-pickupgame,.area-menu,#ntg-recommend,#foot{display:none!important}body{margin:0;padding:0;overflow:hidden}#game_frame{position:fixed;left:50%;top:-16px;margin-left:-600px;z-index:1}.area-pickupgame,.area-menu{display:none!important}")
