# cljever

A Clojure library for the [Clever](http://getclever.com) API.


## Usage

```clojure
(ns your.name.space
    (:require [cljever.core :as cljever]
    	      [cljerver.api.districts :as d]))

(cljever/with-auth "DEMO_KEY" ""
    (let [districts @(d/districts)
    	  data (-> districts :body :data)]
	;; do something with data
))
    	 

```

## License

Copyright © 2013 Josh Rotenberg

Distributed under the Eclipse Public License, the same as Clojure.
