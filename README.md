https://www.youtube.com/watch?v=R2dOyHJu4a8
Creacion de REST API Spring boot PARTE 3
Creacion de REST API Spring boot PARTE 4
Creacion de REST API Spring boot PARTE 5

https://www.objectdb.com/java/jpa/query/jpql/expression
https://www.programcreek.com/java-api-examples/?class=javax.persistence.criteria.CriteriaBuilder&method=equal
https://hellokoding.com/handling-circular-reference-of-jpa-hibernate-bidirectional-entity-relationships-with-jackson-jsonignoreproperties/

http://localhost:8080/order_item/
http://localhost:8080/order_item/?nameItem=Chicken
http://localhost:8080/order_item/?itemId=2
http://localhost:8080/order_item/?nameCustomer=Ameer
http://localhost:8080/order_item/1

{
            "orderItemId": 1,
            "orderId": {
                "orderId": 1,
                "orderNo": "AS-45",
                "customer": {
                    "customerId": 5,
                    "name": "Ayesha Ameer"
                },
                "pmethod": "EFECTIVO",
                "gtotal": 45.000
            },
            "itemId": {
                "itemId": 2,
                "name": "Chicken Tenders w / Fries",
                "price": 4.990
            },
            "quantity": 5
        }
        {"orderId":null,
          "orderNo":"954300",
          "customerId":"1",
          "pmethod":"Cash",
          "gtotal":26,
          "orderItems":[
                        {"orderItemId":null,"orderId":null,"ItemName":"Chicken Tenders","itemId":"1","Price":3.5,"quantity":"1","total":3.5},
                        {"orderItemId":null,"orderId":null,"ItemName":"Soup","itemId":"8","Price":2.5,"quantity":"2","total":5},
                        {"orderItemId":null,"orderId":null,"ItemName":"Chicken Tenders","itemId":"1","Price":3.5,"quantity":"5","total":17.5}
                       ]
        }
        {"orderId":null,
          "orderNo":"984110",
          "customer":{"customerId":"4"},
          "pmethod":"Cash",
          "gtotal":10.98,
          "orderItems":[
                  {"orderItemId":null,"orderId":null,"quantity":"2","total":7,
                      "itemId":{"itemId":"1","name":"Chicken Tenders","price":3.5}},
                  {"orderItemId":null,"orderId":null,"quantity":"2","total":3.98,
                      "itemId":{"itemId":"7","name":"Lettuce and Tomato Burger ","price":1.99}}
                      ]
         }
        
        LISTO FUNCIONA y esta completo el rest api
        Ahora haremos el CORS como parte final
        
        
        customer
        
        
        