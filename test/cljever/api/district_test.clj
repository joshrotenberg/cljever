(ns cljever.api.district-test
  (:use midje.sweet)
  (:require [cljever.core :refer :all]
            [cljever.api.districts :as d]))

(facts "about district api calls"
       (with-auth "DEMO_KEY" ""
         (facts "about the districts call"
                (let [res @(d/districts)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => '(:paging :data :links))
                  (fact "the district's name is Demo District"
                        (-> res :body :data first :data :name) => "Demo District")
                  (fact "there is only one district"
                        (-> res :body :paging :total) => 1)))

         (facts "about the district call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/district id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => (contains [:data :links]))
                  (fact "this is Demo District"
                        (-> res :body :data :name) => "Demo District"
                        (-> res :body :data :id) => id)))

         (facts "about the district schools call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/schools id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => (contains [:paging :data :links]))
                  (fact "contains four schools"
                        (-> res :body :paging :count) => (count (-> res :body :data)))
                  (fact "and those schools match expectations"
                        (get-in (first (-> res :body :data)) [:data :low_grade]) => "6"
                        (get-in (first (-> res :body :data)) [:data :high_grade]) => "8"
                        (get-in (first (-> res :body :data)) [:data :principal :email]) =>
                        "eda_barrows@mailinator.com")))

         (facts "about the district teachers call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/teachers id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district students call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/students id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district sections call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/sections id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district admins call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/admins id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district events call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/events id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district status call"
                (let [id "4fd43cc56d11340000000005"
                      res @(d/status id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))
))

