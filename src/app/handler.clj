(ns app.handler
  (:use compojure.core)
  (:use cheshire.core)
  (:use app.entry)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]))

(defroutes app-routes
           (context "/journal" [] (defroutes documents-routes
                                               (GET "/:account_number" [account_number] (list-entries account_number))
                                               (POST "/" {body :body} (create-entry body))
                                               (context "/:id" [id] (defroutes document-routes
                                                                               (GET "/" [] (get-entry id))
                                                                               ;(PUT "/:id" {body :body} (update-entry id body))
                                                                               ;(DELETE "/" [] (delete-entry id))
                                                                               ))))
           (route/not-found "not found!" ))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))