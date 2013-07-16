(ns cljever.api.section-test
  (:use midje.sweet)
  (:use clojure.pprint)
  (:require [cljever.core :refer :all]
            [cljever.api.sections :as s]))

(facts "about section api calls"
       (with-auth "DEMO_KEY" ""
         (facts "about the sections call"
                (let [res (s/sections)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => '(:paging :data :links))))

         (facts "about the section call"
                (let [res (s/section "50c9f7ccb519c899661852e7")]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => '(:data :links))))

         (fact "the average number of students per section"
                (let [data (get-in (s/sections) [:body :data])
                      num-sections (count data)
                      student-counts (map #(count (-> % :data :students)) data)]
                  (prn (/ (float (reduce + student-counts)) num-sections))
                  ))
         ))
