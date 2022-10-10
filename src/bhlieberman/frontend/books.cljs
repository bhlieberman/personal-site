(ns frontend.books
  (:require [reagent.core :as r]
            [promesa.core :as p]))

(defn books []
  (let [books (r/atom nil)
               fetch-books (fn [] (p/let [_resp (js/fetch "/books/")
                                          data (.json _resp)
                                          out (js->clj data :keywordize-keys true)]
                                    (reset! books out)))
               _ (.addEventListener js/window "load" #(fetch-books))
               _ (.addEventListener js/window "hashchange" (fn [e] (.preventDefault e) (fetch-books)))]
    (fn []
      [:div.container
       [:div {:class "row d-flex"}
        [:div {:class "col"}
         [:div {:class "card m-2 bg-pink-300 p-5 border-4 md:rounded-lg"}
          [:h2 {:class "fs-2 font-mono"} "Filthy Synecdoche"]
          [:p {:class "fs-2 font-mono"} "My first and only novel, an absurdist noir loosely based on some brief moments in the United States Marine Corps and a lot of imagination. Here are some of my influences: "]
          (map (fn [book] [:div {:class "col"}
                           [:div {:class "card m-2 border"}
                            [:img.card-img-top {:src "https://via.placeholder.com/150

C/O https://placeholder.com/"}]
                            [:div {:class "card"}
                             [:h2 {:class "fs-2 font-mono"} (:title book)]
                             [:p {:class "fs-4 font-mono"} (:description book)]]]]) @books)]]]])))

