(ns frontend.core
  (:require [reagent.core :as r]
            ["react-dom/client" :as rdom]
            [promesa.core :as p] 
            [frontend.about :refer [about-me about-site]]
            [frontend.modal :refer [modal]]
            [frontend.books :refer [books]]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]))

(defonce root (rdom/createRoot (.getElementById js/document "app")))

(defonce match (r/atom nil))

(def routes
  [["/"
    {:name ::home
     :view about-me}]
   ["/about"
    {:name ::about
     :view about-site}]
   ["/books"
    {:name ::books
     :view books}]])

(defn page []
  [:div {:class "container"}
   [:div {:class "flex sm:justify-center bg-green-300 border-4 md:rounded-lg"}
    [:nav {:class "flex flex-row sm:justify-center space-x-4 my-auto"}
     [:a {:class "hover:ring-2 ring-lime-300 rounded-lg px-3 py-2
                    text-slate-700 font-medium font-mono hover:bg-slate-100
                    hover:text-slate-900" :href (rfe/href ::home)} "Home"]
     [:a {:class "hover:ring-2 ring-lime-300 rounded-lg px-3 py-2
                    text-slate-700 font-medium font-mono hover:bg-slate-100
                    hover:text-slate-900" :href (rfe/href ::about)} "About"]
     [:a {:class "hover:ring-2 ring-lime-300 rounded-lg px-3 py-2
                    text-slate-700 font-medium font-mono hover:bg-slate-100
                    hover:text-slate-900" :href (rfe/href ::books)} "Books"]]
    [:form {:class "my-2 w-auto my-lg-0"}
     [:input {:class "m-1 shadow-lg border-4 p-2", :type "search", :placeholder "Search", :aria-label "Search"}]
     [:button {:class "btn-primary m-1", :type "submit"} "Search"]]]
   [:div {:class "m-2"} (when-some [m @match]
           (let [view (:view (:data m))]
             [view @match]))]])

(defn app []
  (fn []
    [:div {:class "container space-y-4 mx-auto"}
     [:div>header {:class "text-3xl font-bold py-2 text-center font-mono"} "Ben Lieberman"]
     [page]]))


(defn ^:app/after-load render []
  (.render root (r/as-element [app])))

(defn init []
  (rfe/start!
   (rf/router routes)
   (fn [m] (reset! match m))
   {:use-fragment true})
  (render))

(comment)

