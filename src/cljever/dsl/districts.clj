(ns cljever.dsl.districts
  (:use cljever.core
        cljever.api.districts
        cljever.dsl.common))

(defn-method find-districts districts)

(defn district-name
  [name]
  {:name name})

(defn district-id
  [id]
  {:id id})
