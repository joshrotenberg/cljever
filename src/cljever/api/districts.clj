(ns cljever.api.districts
  (:use cljever.core))

(defmacro defdistricts
  [name doc method resource args]
  `(defcljever ~name ~doc ~method (str "/districts/" ~resource) ~args))

(defdistricts districts "Retrieve all districts" :get "" [])
(defdistricts district  "Retrieve a specific district" :get "%s" [id])
(defdistricts schools   "Retrieve all schools in a district" :get "%s/schools" [id])
(defdistricts teachers  "Retrieve all teachers in a district" :get "%s/teachers" [id])
(defdistricts students  "Retrieve all students in a district" :get "%s/students" [id])
(defdistricts sections  "Retrieve all sections in a district" :get "%s/sections" [id])
(defdistricts admins    "Retrieve all admins in a district" :get "%s/admins" [id])
(defdistricts events    "Retrieve all admins in a district" :get "%s/events" [id])
(defdistricts status    "Retrieve all admins in a district" :get "%s/status" [id])
(defdistricts add-properties    "Add properties to a district" :put "%s/properties" [id])
(defdistricts properties    "Retrieve all properties for a district" :get "%s/properties" [id])

