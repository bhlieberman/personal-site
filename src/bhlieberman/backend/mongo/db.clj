(ns backend.mongo.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [backend.env :refer [env]]
            [cheshire.generate :refer [add-encoder]]
            [monger.json]
            [clojure.edn :as edn])
  (:import org.bson.types.ObjectId)) 

(defn connect-to-mongo []
  (let [_uri (env :MONGO_URI)
        {:keys [_conn db]} (mg/connect-via-uri (env :MONGO_URI))]
    db))

(defn get-books []
  (let [db (connect-to-mongo)
        coll "books"]
    (try (mc/find-maps db coll)
         (catch Exception e
           (println e)))))

(defn get-book-by-title [title]
  (let [db (connect-to-mongo)
        coll "books"]
    (try (mc/find-one-as-map db coll {:title title})
         (catch Exception e
           (println e)))))

(defn add-books [title desc status]
  (let [db (connect-to-mongo)
        coll "books"]
    (try (mc/insert-and-return db coll {:_id (ObjectId.)
                                    :title title
                                    :description desc
                                    :status status})
         (catch Exception e
           (println e)))))

(defn delete-book [title]
  (let [db (connect-to-mongo)
        coll "books"]
    (try (mc/remove db coll {:title title})
         (catch Exception e
           (println e)))))

;; add support for custom serialization of BSON 
(add-encoder org.bson.types.ObjectId
             (fn [c jsonGenerator]
               (.writeString jsonGenerator (str c))))

(comment
  (dotimes [_ 2] (delete-book "Filthy Synecdoche"))
  (map :title (get-books)) 
  (delete-book "Women And Men")
  )

