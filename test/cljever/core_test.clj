(ns cljever.core-test
  (:use midje.sweet)
  (:require [cljever.core :refer :all]))

(facts "about the core functions"
       (with-auth "user" "p@55w3rd"
         (fact "with-auth sets credentials correctly"
               *user* => "user"
               *password* => "p@55w3rd"))
       (fact "keyword-to-fn returns the correct fn"
             ((keyword-to-fn "clojure.core" :map) inc [1 2 3]) => '(2 3 4)
             ((keyword-to-fn "clojure.core" :reduce) + [1 2 3]) => 6))


