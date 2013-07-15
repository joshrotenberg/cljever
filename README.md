# cljever

A Clojure library for the [Clever](http://getclever.com) API.


## Usage

```clojure
;; use the api calls
(ns your.name.space
    (:require [cljever.core :as cljever]
    	      [cljerver.api.districts :as d]))


(cljever/with-auth "DEMO_KEY" ""
    (let [districts (d/districts)
    	  data (-> districts :body :data)]
	;; do something with data
))

;; or the DSL
(ns your.name.space
    (:require [cljever.core :as cljever]
    	      [cljever.dsl.common :as common]
	      [cljever.dsl.districts :as d]))

(cljever/with-auth "DEMO_KEY" ""
	(let [found (d/find-districts
			(d/district-name (c/matches "Something.*")))]

			;; do something with found
))


```

## License

Copyright © 2013 Josh Rotenberg

Distributed under the Eclipse Public License, the same as Clojure.
