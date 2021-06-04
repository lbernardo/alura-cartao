(ns cartao.cc-test
  (:require [clojure.test :refer :all]
            [cartao.cc.logic :refer :all]))

(deftest details-creditcard-test
  (let [data {
              :number  "1111 1111 1111 1111"
              :duedate "11/2022"
              :ccv     111
              :limit   3000
              }
        actual (with-out-str (details-creditcard data))
        expected (with-out-str ((fn [] (println "Número 1111 1111 1111 1111")
                                  (println "Validade 11/2022")
                                  (println "Código de segurança 111")
                                  (println "Limite R$ 3000"))))]
    (is (= actual expected)))
  )
