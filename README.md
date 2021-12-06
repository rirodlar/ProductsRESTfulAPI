# Products RESTful API
Offline Coding Evaluation (Build Restful CRUD API for a Product using Spring Boot, H2, JPA and Hibernate)

## Pre-requisites
Make sure you have installed all the following prerequisites on your development machine:
* **GIT**
* **Java 11**
* **Graddle**

**1. Clone the application**

```bash
git clone https://gitlab.sodimac-it.com/rarodriguezl/products-restful-api.git
```

**2. Run the app using maven**

```bash
./gradlew bootRun
```
The app will start running at <http://localhost:7474>

Configure environment properties on `src/main/resources/environment.properties`


## Explore Rest APIs

The app defines following CRUD APIs.

### Product

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/v1/product/ | Create Product | [JSON](#createProduct) |
| GET   | /api/v1/product | Get All Products  | |
| GET   | /api/v1/product/{sku} | Get Products by sku|
| DELETE   | /api/v1/product/{sku} | Delete Product by sku  |  |
| PATCH   | /api/v1/product/ | Update Product by sku | [JSON](#updateProduct) |
## Sample Valid JSON Request Bodys

##### <a id="createProduct">Add Product -> /api/v1/product</a>
```json
{
  "sku": "FAL-2000068",
  "brand": "B333",
  "size":"Size of the product1",
  "name": "Short description of the product",
  "price": 12,
  "urlImage": "http://www.image.cl",
  "otherImages": [

    {
      "urlImage": "http://www.falalla/image.jpg"
    }
  ]
}
```

##### <a id="updateProduct">Update Product -> /api/v1/product</a>
```json
{
  "brand": "FAL-1111",
  "name": "NAME11"
}
```
###CURL

#### Get All products
```sh
curl --location --request GET 'localhost:7474/api/v1/product/'
```
status:  
200: []  // Array of products  
404: Not Found

#### Get product by filter of SKU  
```sh
curl --location --request GET 'localhost:7474/api/v1/product/FAL-2000049'
```
status:  
200: [] // Array of Products    
404: Not Found  

#### Delete a product by SKU
```sh
curl --location --request DELETE 'localhost:7474/api/v1/product/FAL-2000049'
```
status:  
204: Not Content  
404: Not Found  
500: Server Error  

#### create Product
```sh
curl --location --request POST 'localhost:7474/api/v1/product/' \  
--header 'Content-Type: application/json' \  
--data-raw '{  
"sku": "FAL-2000068",  
"brand": "B",  
"size":"Size of the product1",  
"name": "Short description of the product",  
"price": 12,  
"urlImage": "http://www.falalla/image.jpg",  
"otherImages": [  
{  
"urlImage": "http://www.falalla/image.jpg"  
},  
{
"urlImage": "http://www.falalla/image.jpg"
}  
]  
}'  
```
status:  
201: Created   
400: Bad Request   
500: Server Error  

#### Update a product
```sh
curl --location --request PATCH 'localhost:7474/api/v1/product/FAL-2000049' \  
--header 'Content-Type: application/json' \  
--data-raw '{
    "brand": "x33333"  
}'
```
status:  
200: OK   
400: Bad Request   
404: Not Content   
500: Server Error 
