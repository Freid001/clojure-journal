(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "REST service for documents"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [compojure "1.1.1"]
                 [ring/ring-json "0.4.0"]
                 [c3p0/c3p0 "0.9.1.2"]
                 [com.h2database/h2 "1.3.168"]
                 [cheshire "4.0.3"]]
  :plugins [[lein-ring "0.7.3"]]
  :ring {:handler app.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
