(ns navdist-console.records)

(defrecord ReceivedApi [request-id response url headers])

(defrecord LoadedApiResponse [url headers body])
