(defproject cljever "0.1.0-SNAPSHOT"
  :description "A Clojure library for the Clever REST API"
  :url "https://github.com/joshrotenberg/cljever"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.2"]
                 [org.clojure/data.codec "0.1.0"]
                 [cheshire "5.1.2"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}})

