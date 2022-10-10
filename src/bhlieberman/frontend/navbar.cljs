(ns frontend.navbar
  (:require [reitit.frontend.easy :as rfe]
            [reagent.core :as r]
            [frontend.about :refer [about-site about-me]]
            [frontend.books :refer [books]]))

(defonce match (r/atom nil))

(defn current-page []
  [:div
   [:ul
    [:li [:a {:href (rfe/href ::home)} "Home"]]
    [:li [:a {:href (rfe/href ::about)} "About"]]
    [:li [:a {:href (rfe/href ::books)} "Books"]]]
   (if @match
     (let [view (:view (:data @match))]
       [view @match]))
   #_[:pre (with-out-str (fedn/pprint @match))]])

(def routes
  [["/"
    {:name ::frontpage
     :view about-me}]
   ["/about"
    {:name ::about
     :view about-site}]
   ["/books"
    {:name ::books
     :view books}]])

(defn navbar []
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
    [:button {:class "btn-primary m-1", :type "submit"} "Search"]]])

(comment
  
  )