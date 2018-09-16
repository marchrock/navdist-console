(ns navdist.app.db)

(def app-name
  "Navdist Console")

(def default-db
  {:config
   {:locale :en}
   :state
   {:app-bar {:menu {:open false :target nil}}
    :volume true
    :dialog {:shutdown false}}
   :api {}})
