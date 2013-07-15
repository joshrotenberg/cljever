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
;;  (prn opts)
  {:status status :body (json/parse-string body true)})

(defn transform-where-clause
  [clause]
  (json/generate-string clause))

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
               cljever-params# (last ~with-options)
               resource# (apply format ~resource format-args#)
               options# {:basic-auth [*user* *password*]
                         :query-params cljever-params#
                         :user-agent *user-agent*}]
           ;; build the request and process either a where clause if this is a get and one is present,
           ;; or process properties if this is a put
           (let [response# @(~http-fn (str *api-url* "/" *api-version* resource#)
                                      (if (= ~method :get)
                                        (if-let [where-clause# (:where cljever-params#)]
                                          (assoc-in options# [:query-params :where]
                                                    (json/generate-string where-clause#))
                                          options#)
                                        (assoc (dissoc options# :query-params) :body 
                                               (json/generate-string cljever-params#))))]
             (if-let [error# (:error response#)]
               {:error error#}
               {:status (:status response#)
                :body (json/parse-string (:body response#) true)})))))))
