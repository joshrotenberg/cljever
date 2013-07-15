(ns cljever.dsl.district-test
  (:use midje.sweet)
  (:require [cljever.core :refer :all]
            [cljever.dsl.common :refer :all]
            [cljever.dsl.districts :refer :all]))

(facts "about the district dsl"
       (with-auth "DEMO_KEY" ""
         (let [result (find-districts
                        (district-name "Demo District")
                        (district-id "4fd43cc56d11340000000005"))
               matches-result (find-districts
                                (district-name (matches "Demo.*")))
               failed-result (find-districts
                               (district-name "Other District"))]
           (-> result :body :paging :count) => 1
           (-> matches-result :body :paging :count) => 1
           (-> failed-result :body :paging :count) => 0)))
