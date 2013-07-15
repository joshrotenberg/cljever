(ns cljever.api.district-test
  (:use midje.sweet)
  (:use clojure.pprint)
  (:require [cljever.core :refer :all]
            [cljever.api.common :refer :all]
            [cljever.api.districts :as d]))

(facts "about district api calls"
       (with-auth "DEMO_KEY" ""
         (facts "about the districts call"
                (let [res (d/districts)]
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
                      res (d/district id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body)
                        (keys (-> res :body)) => (contains [:data :links]))
                  (fact "this is Demo District"
                        (-> res :body :data :name) => "Demo District"
                        (-> res :body :data :id) => id)
                  (fact "the call can include second level endpoints"
                        (let [res (-> (d/district id {:include "sections,schools"}) :body :data)
                              sections (:sections res)
                              schools (:schools res)]
                          (-> sections :paging :count) => (count (:data sections))
                          (-> sections :data first :data keys) => (contains [:students :subject :course_name
                                                                             :name :district :teacher :sis_id
                                                                             :term :grade :course_number :created
                                                                             :last_modified :school :id])
                          (-> schools :paging :count) => (count (:data schools))
                          (-> schools :data first :data keys) => (contains [:low_grade :principal :high_grade
                                                                            :name :nces_id :state_id :district
                                                                            :location :sis_id :phone :created
                                                                            :last_modified :school_number :id])))))

         (facts "about the district schools call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/schools id)]
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
                        "eda_barrows@mailinator.com")
                  (fact "the call can include more parameters"
                        (let [school-count (d/schools id {:count true})
                              school-limit (d/schools id {:limit 2 :page 1})
                              school-where1 (d/schools id {:where {:school_number "9255"}})
                              school-where2 (d/schools id {:where {:school_number {:$ne "9255"}}})
                              school-sorted (d/schools id {:sort "principal.name"})]

                          (-> school-count :body :count) => 4
                          (-> school-limit :body :paging :total) => (count (-> school-limit :body :data))
                          (-> school-where1 :body :data first :data :name) => "Clever Academy"
                          (-> school-where2 :body :paging :count) => 3
                          (-> school-sorted :body :data first :data :principal :name) => "Colleen Gottlieb"
                          (-> school-sorted :body :data last :data :principal :name) => "Rudolph Howe"))))

         (facts "about the district teachers call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/teachers id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district students call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/students id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district sections call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/sections id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district admins call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/admins id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district events call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/events id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         (facts "about the district status call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/status id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (keys res) => '(:status :body))))

         ;; the next two facts don't actually work with the DEMO_KEY, but we can at least test that we
         ;; get back a 200 with an error code telling us this
         (facts "about the district add-properties call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/add-properties id {:my-property-name "my property value"})]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (-> res :body :code) => 6
                        (keys res) => '(:status :body))))

         (facts "about the district properties call"
                (let [id "4fd43cc56d11340000000005"
                      res (d/properties id)]
                  (fact "returns a valid response"
                        (:status res) => 200
                        (-> res :body :code) => 12
                        (keys res) => '(:status :body))))))



