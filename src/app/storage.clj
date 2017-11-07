(ns app.storage
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :refer :all]
            [clojure.tools.logging :as log]))

(def db {
   :classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "src/storage/journal.db"
   })

(defn create-db []
  ; uncomment if you want to recreate the table
  ; (execute! db ["drop table if exists entry"])

  ; create tables
  (try
    (db-do-commands db

      ; create entry table
      (create-table-ddl :entry
                        [:id :primary :key]
                        [:account "varchar(255)"]
                        [:timestamp "timestamp"]
                        [:debt "double(9,2)"]
                        [:credit "double(9,2)"]))

    ; log any exceptions
    (catch Exception e
      (log/warn (.getMessage e)))))

; create the db
(create-db)