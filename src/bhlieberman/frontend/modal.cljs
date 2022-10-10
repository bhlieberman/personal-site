(ns frontend.modal
  (:require [frontend.books :refer [books]]
            [reagent.core :as rc]
            [promesa.core :as p]))

(defn modal []
  (let [title (rc/atom nil)
        desc (rc/atom nil)
        add-book (fn [] (p/let [_resp (js/fetch "/books/" (clj->js {:method "POST"
                                                                    :body (.stringify js/JSON #js {:title @title :description @desc :status true})
                                                                    :headers {:Content-Type "application/json"}}))
                                data (.json _resp)]
                          (println data)))]
    (fn []
      [:div {:class "modal", :id "book-modal" :tab-index "-1", :role "dialog"}
       [:div {:class "modal-dialog", :role "document"}
        [:div {:class "modal-content"}
         [:div {:class "modal-header"}
          [:h5 {:class "modal-title"} "Add a book"]
          [:button {:type "button", :class "close", :data-dismiss "modal", :aria-label "Close"}
           [:span {:aria-hidden "true"} "×"]]]
         [:div {:class "modal-body"}
          [:div.d-flex.justify-content-center.text-center
           [:form {:on-submit (fn [^js e] (.preventDefault e))}
            [:div.form-group
             [:div.form-label.text-white "Book"]
             [:input.form-control.m-2 {:type "text"
                                       :placeholder "Enter book title"
                                       :on-change (fn [e] (reset! title (.. e -target -value)))}]
             [:input.form-control.m-2 {:type "text"
                                       :placeholder "Book description"
                                       :on-change (fn [e] (reset! desc (.. e -target -value)))}]
             [:button.m-2 {:on-click #(add-book) :type :submit} "Add book"]]]]]
         [:div {:class "modal-footer"}
          [:button {:type "button", :class "btn btn-primary"} "Save changes"]
          [:button {:type "button", :class "btn btn-secondary", :data-dismiss "modal"} "Close"]]]]])))

