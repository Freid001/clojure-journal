(ns app.entry
  (:use app.storage)
  (:use ring.util.response)
  (:require [clojure.java.jdbc :refer :all]))

; get an entry from the journal
(defn get-entry [id]
  (let [result (query db ["select * from entry where entry_id =?", id])]
    (if (empty? result) (not-found ()) (response (first result)))))

; get balance from journal for a specific account number.
(defn get-balance [account_number]
  (let [result (query db ["select (SUM(debit) - (SUM(credit))) AS balance
                           from entry where account_number =?", account_number])]
    (if (empty? result) (not-found ()) (response (first result)))))

; list all entries from the journal for a specific account number.
(defn get-ledger [account_number]
  (let [result (query db ["select e.*, (SUM(ee.debit) - (SUM(ee.credit))) AS balance
                           from 'entry' AS e
                           left join entry AS ee ON ee.`entry_id` <= e.`entry_id` AND ee.account_number = e.account_number
                           where e.account_number = ?
                           group by e.entry_id, e.account_number
                           order by timestamp DESC", account_number])]
    (if (empty? result) (not-found ())) (response (vec result))))

; create a new entry in the journal
(defn create-entry [doc]
  (let [entry (as-> {} e
                    (assoc e "account_number" (if (integer? (get doc "account_number")) (get doc "account_number") (try (Integer/parseInt (get doc "account_number")) (catch Exception e nil))))
                    (assoc e "credit" (if (float? (get doc "credit")) (get doc "credit") (try (Float/parseFloat (get e "credit")) (catch Exception e 0))))
                    (assoc e "debit" (if (float? (get doc "debit")) (get doc "debit") (try (Float/parseFloat (get e "debit")) (catch Exception e 0))))
                    (assoc e "description" (if (empty? (get doc "description")) ("") (get doc "description")))
                    (assoc e "reference" (if (empty? (get doc "reference")) ("") (get doc "reference")))
                    (assoc e "timestamp" (quot (System/currentTimeMillis) 1000)))
        errors (as-> () err
                     (if (= (get entry "account_number") nil)
                       (conj err "An account_number is required.") err)
                     (if (< (get entry "credit") 0)
                       (conj err "Credit can not be a negative number.") err)
                     (if (and (= (get entry "debit") 0) (= (get entry "credit") 0))
                       (conj err "Either debit or credit is required.") err)
                     (if (< (get entry "debit") 0)
                       (conj err "Debit can not be negative number.") err)
                     (if (and (> (get entry "debit") 0) (> (get entry "credit") 0))
                       (conj err "Only either debit or credit allowed per entry.") err))]

    (if (= errors ())
      (let [insert (insert! db :entry entry) last_entry_id (query db ["SELECT MAX(entry_id) AS id FROM entry"])]
        (get-entry (get (first last_entry_id) :id)))
      (response {:errors errors}))))