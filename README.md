# NBP Postman Collection

This Postman Collection includes API requests for performing currency exchange rate calculations and fetching exchange rates.

## API Requests

### Calculate

- **Name:** Calculate
- **Method:** POST
- **URL:** [localhost:8080/exchange-rate/calculate](localhost:8080/exchange-rate/calculate)
- **Description:** This request is used to calculate the exchange rates for multiple currencies on a specific date. The request body contains the date and forex requests with amounts and currency codes.

#### Request Body
 ```
 {
    "date": "2023-07-07",
    "forexRequests" : [
        {
            "amount": 100,
            "code": "USD"
        },
        {
            "amount": 10000,
            "code": "THB"
        },
        
        {
            "amount": 10000,
            "code": "INR"
        }
    ]
}
 ```



### FETCH Exchange rate

- **Name:** FETCH Exchange rate
- **Name:** FETCH Exchange rate
- **Method:** GET
- **URL:** [http://localhost:8080/exchange-rate?forDate=2023-07-07&code=THB](http://localhost:8080/exchange-rate?forDate=2023-07-07&code=THB)
- **Description:** This request is used to fetch the exchange rate for a specific currency on a specific date. The query parameters include the date and currency code.

#### Query Parameters

- `forDate`: 2023-07-07
- `code`: THB



# Note:

    The postman collection for this root folder name: NBP.postman_collection.json