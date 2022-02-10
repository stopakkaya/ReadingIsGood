Reading Book is Good App : https://github.com/stopakkaya/ReadingIsGood.git

API Design :

	Response consists 3 base field when api successses : 
		1 - return Type : 0 (Successfull)
		2 - isPaginated : true/false (When request is paginated)
		3 - "resultMessage": "Operation Successful"
		4 - content : (If data available)

	Response consists 4 base field when api failed or business exception :
		1 - return Type : -1 (Fail)
		2 - resultMessage : Operation failed
		3 - errors : Error detail
		4 - isPaginated : true/false

Validation :
Error checking is done on all endpoints. 
If something is wrong about business flow, the user is informed over Business Exceptions.

Logging :
Api calls are logged.

Swagger:
Swagger address : http://localhost:8080/swagger-ui.html




1-Authentication

For any request to any endpoint user must have Bearer Token over generate-token.
For all steps, user puts headers or authorization info on Postman. After token generated a string value generates. 
"Bearer " + GeneratedString =  Authentication Token
Otherwise user gets 403 HttpStatus.

Endpoint : http://localhost:8080/generate-token (POST)

Request Payload : {
"email": "samettopakkaya@gmail.com",
"password": "password123"
}
Response : token


2 - Create Customer

User cannot register twice with same email address. Otherwise, gets "Email already exist!".

Endpoint :  http://localhost:8080/retail/v1/customer/create-customer (POST)

Request Payload : {
"name" : "Samet",
"lastName" : "Topakkaya",
"phone" : "5068579264",
"email" : "testuser@test.com",
"password" : "password123"
}


3 - List All Orders Of Customer

With pagination all orders of customer will be listed.

Endpoint : http://localhost:8080/retail/v1/customer/customer-order/{customerId} (GET)

Request Payload : “”

4 - Create New Book

Book stores unique by author,book name and publisher. 
When user try to save same book to db, gets "Book already exist for this author and publisher" Exception

Endpoint : http://localhost:8080/retail/v1/book/create-book (POST)

Request Payload : {
"name" : "Sokratesin Savunması",
"author" : "Platon",
"isbn" : "9786053607021",
"publishYear" : 2012,
"publisher" : "Türkiye İş Bankası Yayınları",
"stockSize" : 1000,
"price" : 38
}


5- Update Book Stock

Api updates book stock size.

Endpoint : http://localhost:8080/retail/v1/book/update-stock (PUT)

Request Payload : {
"bookId" : 1,
"stockSize" : 50
}


6 - Create Order

There is no status for order because of there is no demand for order canceling. All orders are assumed to be delivered.
Status field is may add after.

-Book's stock size increasing after any order.
-If there is no book stock for demanding order, transaction will be end with Exception.
-If two user try to buy same book system block one of them.
In this way the last book cannot be sold to two customer at same time.
First user gets book and the other gets Exception about stock size.

Endpoint : http://localhost:8080/retail/v1/order/create-order (POST)

Request Payload : {
"orderDate" : "2022-10-04T13:34:00.000",
"deliverDate" : "2018-03-29T13:34:00.000",
"customerId" : 1,
"orderAmount" : 12,
"bookId" : 1
}


7- Query For Order Id

Query for Order.

Endpoint : http://localhost:8080/retail/v1/order/{orderId} (GET)

Request Payload : “”


8 - List Orders For Date Interval

All orders will be listed between start date and end date.

Endpoint : http://localhost:8080/retail/v1/order/query-order (GET)

Request Payload : {
"startDate" : "2020-03-28",
"endDate" : "2023-08-02"
}


9- Customer Statistic

User's monthly statistics are lists.

Endpoint : http://localhost:8080/retail/v1/statistic/{email} (GET)

Request Payload : “”