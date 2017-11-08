(ns app.entry
  (:use app.storage)
  (:use ring.util.response)
  (:require [clojure.java.jdbc :refer :all]))

; entry
(defn entry [doc]
  (as-> doc e
        (if (empty? (get e "id")) (assoc e "id" (str (java.util.UUID/randomUUID))) e)
        (if (empty? (get e "timestamp")) (assoc e "timestamp" (quot (System/currentTimeMillis) 1000)) e)
        (if (empty? (get e "debit")) (assoc e "debit" 0) e)
        (if (empty? (get e "credit")) (assoc e "credit" 0) e)
        (if (integer? (get e "account_number")) e (assoc e "account_number"
                                                           (try (Integer/parseInt (get e "account_number"))
                                                                (catch Exception e nil))))
        (if (integer? (get e "debit")) e (assoc e "debit"
                                                  (try (Integer/parseInt (get e "debit"))
                                                       (catch Exception e 0))))
        (if (integer? (get e "credit")) e (assoc e "credit"
                                                   (try (Integer/parseInt (get e "credit"))
                                                        (catch Exception e 0))))))

; list all entries in the journal
(defn list-entries [account_number]
  (let [result (query db ["select * from entry where account_number =?", account_number])]
    (if (empty? result) (not-found ())) (response (vec result))))

; get a specific entry in the journal
(defn get-entry [id]
  (let [result (query db ["select * from entry where id =?", id])]
    (if (empty? result) (not-found ()) (response (first result)))))

; create a new entry in the journal
(defn create-entry [doc]
  (let [e (entry doc) err ()]
    (let [err (if (= (get e "account_number") nil) (conj err "an account_number is required") err)]
      (let [err (if (and (<= (get e "debit") 0) (<= (get e "credit") 0)) (conj err "either debit or credit is required") err)]
        (if (= () err)
          (insert! db :entry entry)
        (response {:err err}))))))


;(defn foo-bar [doc]
;  (let [err []]
;    (if (= (get doc "foo") nil) (conj err "foo is required"))
;    (if (= (get doc "bar") nil) (conj err "bar is required"))
;
;    (if (not-empty err)
;      (prn err)
;        (
;          ;do somthing
;        )
;      )
;    )
;  )




;(let [id (str (java.util.UUID/randomUUID))]
;  (let [entry (assoc doc "id" id)]
;    (insert! db :entry entry))
;  (get-entry id)))

;(defn update-entry [id doc]
;  (let [entry (assoc doc "id" id)]
;    (update! db :entry entry ({:id id})))
;
;  (get-entry id))
;
;(defn delete-entry [id]
;  (delete! db :entry ({:id id}))
;  status (204))