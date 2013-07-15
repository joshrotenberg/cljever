(ns cljever.core
  (:require [org.httpkit.client :as http]
            [cheshire.core :as json]))

(def ^:dynamic *user* nil)
(def ^:dynamic *password* nil)
(def ^:dynamic *user-agent* "cljever/0.1.0")
(def ^:dynamic *api-url* "https://api.getclever.com")
(def ^:dynamic *api-version* "v1.1")

(defmacro with-auth
  [user password & body]
  `(binding [*user* ~user
             *password* ~password]
     ~@body))

(defn keyword-to-fn
  "Given a namespace (as a string) and a function named by a keyword, resolves the function and returns it."
  [ns k]
  (ns-resolve (symbol ns) (symbol (name k))))

(defn response-handler
  [{:keys [status headers body error opts] :as response}]
  {:status status :body (json/parse-string body true)})

(defmacro defcljever
  "Generate a Clever API call function. Takes the function name, doc,
  an HTTP method (keyword, i.e. :get), the URI and a vector of
  arguments the fuction should accept. It will automatically create
  an overloaded version that also accepts a map of options."
  [name doc method resource args]
  (let [with-options (conj args (symbol "options"))
        fname (symbol name)
        http-fn (keyword-to-fn "org.httpkit.client" method)]
    `(defn ~fname
       ~doc
       (~args
        (apply ~fname (conj ~args {})))
       (~with-options
         (let [format-args# (first (split-with (complement map?) ~args))
               options# {:basic-auth [*user* *password*]
                         :query-params (last ~with-options)
                         :user-agent *user-agent*}
               resource# (apply format ~resource format-args#)]
           (~http-fn (str *api-url* "/" *api-version* resource#) options# response-handler))))))
