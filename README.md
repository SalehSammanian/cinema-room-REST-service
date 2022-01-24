# cinema-room-REST-service
A REST implementation of Virtual Cinema Theater using Spring Boot and built with Intellij IDEA.


### About
Always wanted to have your private movie theater and screen only the movies you like? You can buy a fancy projector and set it up in a garage, but how can you sell tickets? Having a booth is old-fashioned, so let's create a special service for that! Make good use of Spring and write a REST service that can show the available seats, sell and refund tickets, and display the statistics of your venue. Pass me the popcorn, please!

### Learning outcomes
In this project, I learned how to create a Spring REST service that will help you manage a small movie theatre. Handle HTTP requests in a controller, create services and respond with JSON objects.


## Example 1: a <code>GET /seats</code> request
Response body:
```
{
   "total_rows":9,
   "total_columns":9,
   "available_seats":[
      {
         "row":1,
         "column":1,
         "price":10
      },
      {
         "row":1,
         "column":2,
         "price":10
      },
      {
         "row":1,
         "column":3,
         "price":10
      },

      ........

      {
         "row":9,
         "column":8,
         "price":8
      },
      {
         "row":9,
         "column":9,
         "price":8
      }
   ]
}
```
## Example 2a: Buying and returning a ticket (a <code>POST /purchase</code> request)
Request body:
```
{
    "row": 3,
    "column": 4
}
```
Response body:
```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556",
    "ticket": {
        "row": 3,
        "column": 4,
        "price": 10
    }
}
```

## Example 2b: <code>POST /return</code> with the correct token
Request body:
```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```
Response body:
```
{
    "returned_ticket": {
        "row": 1,
        "column": 2,
        "price": 10
    }
}
```

## Example 2c: <code>POST /return</code> with an expired token
Request body:
```
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```
Response body:
```
{
    "error": "Wrong token!"
}
```

## Example 2d: a <code>POST /purchase</code> request, the ticket is already booked
Request body:
```
{
    "row": 3,
    "column": 4
}
```

Response body:
```
{
    "error": "The ticket has been already purchased!"
}
```

## Example 2e: a <code>POST /purchase</code> request, a wrong row number
Request body:
```
{
    "row": 15,
    "column": 4
}
```
Response body:
```
{
    "error": "The number of a row or a column is out of bounds!"
}
```

## The Statistics :
Endpoint /stats will handle POST requests with URL parameters. If the URL parameters contain a password key with a super_secret value, it will return the movie theatre statistics in the following format:
## Example 3a:  a <code>POST /stats</code> request with no parameters
Response body:
```
{
    "error": "The password is wrong!"
}
```

## Example 3b:  a <code>POST /stats</code> request with the correct password == super_secret
Response body:
```
{
    "current_income": 30,
    "number_of_available_seats": 78,
    "number_of_purchased_tickets": 3
}
```
