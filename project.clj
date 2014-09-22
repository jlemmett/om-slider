(defproject om-slider "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.7.1"]
                 [prismatic/om-tools "0.3.2"]
                 [jayq "2.5.2"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "om-slider"
              :source-paths ["src"]
              :compiler {
                :output-to "om_slider.js"
                :output-dir "out"
                :optimizations :none
                :externs ["lib-ext/jquery.nouislider.all.js", "lib-ext/jquery.1.11.1.min.js"]
                :source-map true}}]})
