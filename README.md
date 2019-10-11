https://www.youtube.com/watch?v=R2dOyHJu4a8
Creacion de REST API Spring boot PARTE 3
Creacion de REST API Spring boot PARTE 4
Creacion de REST API Spring boot PARTE 5

https://www.objectdb.com/java/jpa/query/jpql/expression
https://www.programcreek.com/java-api-examples/?class=javax.persistence.criteria.CriteriaBuilder&method=equal

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
        
        LISTO FUNCIONA y esta completo el rest api
        Ahora haremos el CORS como parte final