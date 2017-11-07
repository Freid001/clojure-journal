(ns app.entry
  (:use app.storage)
  (:use ring.util.response)
  (:require [clojure.java.jdbc :refer :all]))

; list all entries in the journal
(defn list-entries []
  (let [result (query db ["select * from entry"])]
    (if (empty? result) (not-found ())) (response (vec result))))

; get a specific entry in the journal
(defn get-entry [id]
  (let [result (query db ["select * from entry where id =?", id])]
    (if (empty? result) (not-found ()) (response (first result)))))

; create a new entry in the journal
(defn create-entry [doc]
  (let [err []]
    (let [id (str (java.util.UUID/randomUUID))]
      (let [entry (assoc doc "id" id)]

        ; missing input
        (if (empty? (get entry "debt")) ())

        (prn err)

        ))))

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