(ns cartao.client-test
  (:require [clojure.test :refer :all]
            [cartao.client.logic :refer :all]))

(def database {
                        :name "Joao Test"
                        :cpf "00000000000000"
                        :email "joao.test@getnada.com"
                        }
)
(def expected
  (with-out-str ((fn [] (println "Nome: Joao Test")
                   (println "CPF: 00000000000000")
                   (println "Email: joao.test@getnada.com"))) ))
(deftest client-info-test
  (testing "Client info"
    (is (= (with-out-str (client-info database)) expected))))
