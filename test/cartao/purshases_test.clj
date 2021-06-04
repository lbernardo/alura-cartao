(ns cartao.purshases_test
  (:require [clojure.test :refer :all]
            [cartao.purchases.logic :refer :all]))


(defn- execute-test [func test]
  (testing (:testing test)
    (let [actual (with-out-str (func (:database test)))]
      (is (= (:expected test) actual)))))

(deftest purchases-list-test
  (let [func (partial execute-test purchases-list)
        data [{
               :database []
               :testing  "Testing without purchases"
               :expected (with-out-str ((fn [] (print ""))))
               }
              {
               :database [{:date "2021-01-01" :value "2000" :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with only purchase"
               :expected (with-out-str ((fn [] (println "2021-01-01 R$ 2000 Test TestingShop"))))
               }
              {
               :database [{:date "2021-01-01" :value "2000" :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value "3000" :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with one more purchase"
               :expected (with-out-str ((fn [] (println "2021-01-01 R$ 2000 Test TestingShop")
                                          (println "2021-01-02 R$ 3000 Test TestingShop"))))
               }
              {
               :database [{:date "2021-01-01" :value "2000" :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value "3000" :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-03" :value "4000" :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-04" :value "5000" :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with many purchases"
               :expected (with-out-str ((fn [] (println "2021-01-01 R$ 2000 Test TestingShop")
                                          (println "2021-01-02 R$ 3000 Test TestingShop")
                                          (println "2021-01-03 R$ 4000 Test TestingShop")
                                          (println "2021-01-04 R$ 5000 Test TestingShop"))))
               }
              ]]
    (run! func data)))

(deftest total-by-category-test
  (let [func (partial execute-test amount-by-category)
        data [{
               :database []
               :testing  "Testing without purchases"
               :expected (with-out-str ((fn [] (print ""))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with only purchase"
               :expected (with-out-str ((fn [] (println "Test R$ 2000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with one more purchase"
               :expected (with-out-str ((fn [] (println "Test R$ 5000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-03" :value 4000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-04" :value 5000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with many purchases"
               :expected (with-out-str ((fn [] (println "Test R$ 14000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test1 :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test1 :establishment "TestingShop"}
                          {:date "2021-01-03" :value 4000 :category "Test2" :category-id :test2 :establishment "TestingShop"}
                          {:date "2021-01-04" :value 5000 :category "Test3" :category-id :test3 :establishment "TestingShop"}
                          {:date "2021-01-05" :value 6000 :category "Test2" :category-id :test2 :establishment "TestingShop"}]
               :testing  "Testing with many purchases with many categories"
               :expected (with-out-str ((fn [] (println "Test R$ 5000")
                                          (println "Test2 R$ 10000")
                                          (println "Test3 R$ 5000"))))
               }
              ]]
    (run! func data)))

(deftest amount-invoice-test
  (let [func (partial execute-test amount-invoice)
        data [{
               :database []
               :testing  "Testing without purchases"
               :expected (with-out-str ((fn [] (println "R$ 0"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with only purchase"
               :expected (with-out-str ((fn [] (println "R$ 2000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with one more purchase"
               :expected (with-out-str ((fn [] (println "R$ 5000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-03" :value 4000 :category "Test" :category-id :test :establishment "TestingShop"}
                          {:date "2021-01-04" :value 5000 :category "Test" :category-id :test :establishment "TestingShop"}]
               :testing  "Testing with many purchases"
               :expected (with-out-str ((fn [] (println "R$ 14000"))))
               }
              {
               :database [{:date "2021-01-01" :value 2000 :category "Test" :category-id :test1 :establishment "TestingShop"}
                          {:date "2021-01-02" :value 3000 :category "Test" :category-id :test1 :establishment "TestingShop"}
                          {:date "2021-01-03" :value 4000 :category "Test2" :category-id :test2 :establishment "TestingShop"}
                          {:date "2021-01-04" :value 5000 :category "Test3" :category-id :test3 :establishment "TestingShop"}
                          {:date "2021-01-05" :value 6000 :category "Test2" :category-id :test2 :establishment "TestingShop"}]
               :testing  "Testing with many purchases with many categories"
               :expected (with-out-str ((fn [] (println "R$ 20000"))))
               }
              ]]
    (run! func data)))