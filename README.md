# Products RESTful API
Offline Coding Evaluation (Crud Productos)

## Pre-requisites
Make sure you have installed all the following prerequisites on your development machine:
* Git
* Java 11

Configure environment properties on `src/main/resources/environment.properties`

api.port=7474
api.prefix=/api/v1

BD: H2(default), Postgres
 

### To execute the project
```
./gradlew bootRun
```

###CURL

#### Get All products

curl --location --request GET 'localhost:7474/api/v1/product/'
status:
200: [] // Array de productos
404: Not Found

#### Get product by filter of SKU
curl --location --request GET 'localhost:7474/api/v1/product/FAL-2000049'

status:
200: [] // Array de productos
404: Not Found

#### Delete a product by SKU

curl --location --request DELETE 'localhost:7474/api/v1/product/FAL-2000049'

status:
204: Not Content
404: Not Found
500: Server Error

#### create Product

curl --location --request POST 'localhost:7474/api/v1/product/' \
--header 'Content-Type: application/json' \
--data-raw '{
"sku": "FAL-2000068",
"brand": "B",
"size":"Size of the product1",
"name": "Short description of the product",
"price": 12,
"urlImage": "http://www.image.cl",
"otherImages": [
{
"urlImage": "http:ww.image.jpg"
},
{
"urlImage": "http://www.image.png"
}
]
}'

status:
201: Created 
400: Bad Request 
500: Server Error

#### Update a product
curl --location --request PATCH 'localhost:7474/api/v1/product/FAL-2000049' \
--header 'Content-Type: application/json' \
--data-raw '{

    "brand": "x33333"

}'
