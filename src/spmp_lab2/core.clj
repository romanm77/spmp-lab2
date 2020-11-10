(ns spmp-lab2.core
  (:require [clojure.core.async :as a]))

(def c1 (a/chan 10))
(a/onto-chan c1 [9 7 5 8 2 1 20 7 8 ])
(def c2 (a/chan 10))

(defn func2 [chan1 chan2 n]
  (a/go-loop [p 0]
     (when-some [v (a/<! chan1)]
       (when (< n (- v p))
         (a/>! chan2 v))
       (recur v))))

(func2 c1 c2 2)

(defn consumer [c]
  (Thread/sleep 1000)
  (a/go-loop []
     (when-some [value (a/<! c)]
       (println "received " value)
       (recur))))

(consumer c2)