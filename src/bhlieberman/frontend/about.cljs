(ns frontend.about
  (:require [reitit.frontend.easy :as rfe]))

#_{:clj-kondo/ignore [:redefined-var]}
(defn about-me []
  [:div {:class "bg-pink-300 p-5 border-4 md:rounded-lg" :id "about"}
   [:div {:class "flex-initial"}

    [:h1 {:class "font-mono text-center fs-2"} "About me"]
    [:p {:class "font-mono"} "My name is Ben. I am a native of Baltimore, Maryland, USA. I turned to programming in my late twenties after
                              some failed attempts to grok abstract mathematics. Currently, I am learning about web development in JavaScript, but
                              I am more interested in backend roles. You can see some of my work on my " 
     [:a {:class "underline text-blue-600" :href "https://github.com/bhlieberman"} "Github"] 
     ". In my free time, I enjoy reading, writing, and as much exercise as I can get. I hope to one day write full-time and do some freelance programming as it suits me."]]])

(defn about-site []
  [:div {:class "bg-pink-300 p-5 border-4 md:rounded-lg" :id "site-info"}
   [:div {:class "flex-initial"}
    [:h1 {:class "font-mono text-center fs-3"} "About the site"]
    [:p {:class "font-mono"} "This is my website. There are many like it, but this one is mine.
                              It is built on Clojure, a programming language I love using for its simplicity and power."]]])