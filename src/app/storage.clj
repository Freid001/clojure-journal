(ns app.storage
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :refer :all]
            [clojure.tools.logging :as log]))

(def db {
   :classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "resources/storage/journal.db"
   })

(defn create-db []
  ; comment if you don't want to recreate the table during compilation
   (execute! db ["drop table if exists entry"])

  ; create tables
  (try
    (db-do-commands db

      ; create entry table
      (create-table-ddl :entry
                        [:entry_id "integer" "PRIMARY KEY" "AUTOINCREMENT"]
                        [:reference "string"]
                        [:account_number "integer(255)"]
                        [:timestamp "timestamp"]
                        [:description "text"]
                        [:debit "float(10,2)"]
                        [:credit "float(10,2)"]))

    ; log any exceptions
    (catch Exception e
      (log/warn (.getMessage e)))))

; create the db
(create-db)