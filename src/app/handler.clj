(ns app.handler
  (:use compojure.core)
  (:use cheshire.core)
  (:use app.entry)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]))

(defroutes app-routes
           (context "/journal" [] (defroutes journal-routes
                                             (context "/:account_number" [account_number] (defroutes account-number-routes
                                                                                                     (GET "/balance" [account_number] (get-balance account_number))
                                                                                                     (GET "/entries" [account_number] (get-entries account_number))))
                                             (context "/entry" [] (defroutes entry-routes
                                                                             (GET "/:id" [id] (get-entry id))
                                                                             (PUT "/" {body :body} (create-entry body))))))
           (route/not-found (response {:errors ["page not found."]})))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))