(ns backend.server.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pprint]
            [clojure.string :as str]
            [cheshire.core :refer [generate-string parse-string]]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring :as ring]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :as jetty]
            [backend.mongo.db :refer [get-books get-book-by-title add-books delete-book]]))

(defonce server (atom nil))

(defn index [] (slurp (io/resource "public/index.html")))

(defn get-all-books []
  (let [json (generate-string (get-books))]
    {:body json :status 200}))

(defn get-one [{{:keys [title]} :path-params}]
  (let [title (str/join " " (str/split title #"\-"))
        json (generate-string (get-book-by-title title))]
    {:body json :status 200}))

(defn add-book [{{:keys [title description status]} :body-params}]
  (let [book (add-books title description status)
        resp (generate-string book)]
    {:body resp :status 201}))

(defn delete-book-by-name [{{:keys [title]} :path-params}]
  (let [book (delete-book title)
        resp (generate-string book)]
    {:body resp :status 204}))

(def routes
  (ring/ring-handler
   (ring/router
    ["/"
     ["ping/" {:handler (fn [req] {:body (with-out-str (pprint/pprint req)) :status 200})}]
     ["books/"
      {:post (fn [req] (add-book req))
       :get (fn [_req] (get-all-books))
       :delete (fn [req] (delete-book-by-name req))}] ;; Mongo
     ["assets/*" (ring/create-resource-handler {:root "public/assets"})]
     ["" {:handler (fn [_] {:body (index) :status 200})}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(def app (-> routes
             wrap-keyword-params
             wrap-params
             wrap-reload))

(defn start-server "Start the Jetty server" []
  (swap! server assoc :jetty (jetty/run-jetty #'app {:port 3001 :join? false})))

(defn stop-server "Stop the Jetty server" []
  (when-some [s @server]
    (.stop (:jetty s))
    (reset! server nil)))

(comment
  (start-server)
  (stop-server)
  )