(ns app.entry
  (:use app.storage)
  (:use ring.util.response)
  (:require [clojure.java.jdbc :refer :all]))

; get a specific entry in the journal
(defn get-entry [id]
  (let [result (query db ["select * from entry where id =?", id])]
    (if (empty? result) (not-found ()) (response (first result)))))

; calculate total balance in journal
(defn get-balance [account_number]
  (let [result (query db ["select SUM(debit) AS total_debit, SUM(credit) AS total_credit, (SUM(debit) - SUM(credit)) AS total_balance from entry where account_number =?", account_number])]
    (if (empty? result) (not-found ()) (response (first result)))))

; list all entries in the journal
(defn get-entries [account_number]
  (let [result (query db ["select * from entry where account_number =? order by timestamp desc", account_number])]
    (if (empty? result) (not-found ())) (response (vec result))))

; create a new entry in the journal
(defn create-entry [doc]
  (let [entry (as-> {} e
                    (assoc e "id" (str (java.util.UUID/randomUUID)))
                    (assoc e "timestamp" (quot (System/currentTimeMillis) 1000))
                    (assoc e "account_number" (if (integer? (get doc "account_number")) (get doc "account_number") (try (Integer/parseInt (get doc "account_number")) (catch Exception e nil))))
                    (assoc e "debit" (if (integer? (get doc "debit")) (get doc "debit") (try (Integer/parseInt (get e "debit")) (catch Exception e 0))))
                    (assoc e "credit" (if (integer? (get doc "credit")) (get doc "credit") (try (Integer/parseInt (get e "credit")) (catch Exception e 0)))))
        errors (as-> () err
                     (if (= (get entry "account_number") nil) (conj err "an account_number is required.") err)
                     (if (and (<= (get entry "debit") 0) (<= (get entry "credit") 0)) (conj err "either debit or credit is required.") err))]

    (if (= errors ())
      (let [insert (insert! db :entry entry)]
        (get-entry (get entry "id")))
      (response {:errors errors}))))