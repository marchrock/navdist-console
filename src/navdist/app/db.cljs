(ns navdist.app.db)

(def default-db
  {:config {}
   :state
   {:app-bar {:menu {:open false :target nil}}
    :volume true
    :dialog {:shutdown false}}
   :api {}})
