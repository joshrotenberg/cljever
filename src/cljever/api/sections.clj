(ns cljever.api.sections
  (:use cljever.core))

(defmacro defsections
  [name doc method resource args]
  `(defcljever ~name ~doc ~method (str "/sections/" ~resource) ~args))

(defsections sections "Retrieve all sections" :get "" [])
(defsections section "Retrieve a specific section" :get "%s" [id])
(defsections school "Retrieve information about a school for a section" :get "%s/school" [id])
(defsections district "Retrieve information about a district for a section" :get "%s/district" [id])
(defsections students "Retrieve a list of students for a section" :get "%s/students" [id])
(defsections teacher "Retrieve information about the teacher for a section" :get "%s/teacher" [id])
(defsections events "Retrieve events for a section" :get "%s/events" [id])
(defsections properties "Retrieve properties for a section" :get "%s/properties" [id])
(defsections add-properties "Add properties for a section" :put "%s/properties" [id])
(defsections property "Add properties for a section" :get "%s/properties/%s" [id property])
