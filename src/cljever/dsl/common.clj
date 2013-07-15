(ns cljever.dsl.common
  (:use cljever.core))

(defn query
  [& criteria]
  (apply merge criteria))

(defmacro defn-method
  [name fn]
  `(defn ~name [& criteria#]
       (~fn {:where (apply query criteria#)})))

(defn is-greater-than
  [arg]
  {:$gt arg})

(defn is-less-than
  [arg]
  {:$lt arg})

(defn in-the-range
  [low high]
  {:$gte low, :$lte high})

(defn is-in
  [c]
  {:$in c})

(defn is-not-in
  [c]
  {:$nin c})

(defn matches
  [arg]
  {:$regex arg})
