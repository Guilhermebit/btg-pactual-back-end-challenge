#  BTG Pactual Back-end Challenge
[![Docker Pulls](https://badgen.net/docker/pulls/guibitencurt/order-microservice-rabbit-mongo)](https://hub.docker.com/r/guibitencurt/order-microservice-rabbit-mongo)
[![CI](https://github.com/Guilhermebit/btg-pactual-back-end-challenge/actions/workflows/ci.yml/badge.svg)](https://github.com/Guilhermebit/btg-pactual-back-end-challenge/actions/workflows/ci.yml)
[![licence mit](https://img.shields.io/badge/licence-MIT-blue.svg)](https://github.com/Guilhermebit/btg-pactual-back-end-challenge/blob/master/LICENSE)

Microservice developed to address the [BTG Pactual challenge](./PROBLEM.md). The challenge involves creating a microservice that:
- Consumes data from a queue
- Stores this data in a database
- Provides functionality to:
    - Retrieve the total amount of an order
    - Get the total number of orders for a customer
    - List orders made by a customer


## This project has the following architecture: 
<p align="center">
  <img src="https://github.com/user-attachments/assets/05fff4db-31ea-49a1-9162-0cc5cc1395b4" alt="Microservice Architecture">
</p>

## Tecnologies
- ✅ Java 21
- ✅ SpringBoot
- ✅ Spring Data MongoDB
- ✅ RabbitMQ
- ✅ Docker
- ✅ JUnit 5 + Mockito(unit tests)
- ✅ IntelliJ IDEA Community Edition 2023.2.2

## :rocket: Installation
1. Clone the repository:
```
$ git clone https://github.com/Guilhermebit/btg-pactual-back-end-challenge.git
```
2. Install dependencies with Maven

3. Install docker: https://docs.docker.com/engine/install/

4. Run the following command:
```
./mvnw clean install
```
## Usage
1. Run the following command:
```
docker-compose up 
```
2. Access RabbitMQ at http://localhost:15672
- Login using the default credentials (usually `guest`/`guest`).
- Navigate to the "Queues" menu and select the relevant queue.
- Paste the provided payload from the challenge into the Payload field.
- Click on "Publish Message" to send the message to the queue.
3. The API will be accessible at http://localhost:8081.
## Api EndPoints 
To test the HTTP requests, you can use Postman.<br />
Download Postman here: https://www.postman.com/downloads/

## List customer orders
`GET /customers/{customerId}/orders`
+ Response 200 (application/json)

  + Body

     ```json
     {
       "data": [
         {
           "orderId": 1001,
           "customerId": 1,
           "total": 120.00
         }
       ],
       "pagination": {
         "page": 0,
         "pageSize": 10,
         "totalElements": 1,
         "totalPages": 1
       }
     }
    ```
## Troubleshooting

If you encounter issues, consider the following:
- Ensure all services are running properly in Docker.
- Check Docker logs for any errors related to RabbitMQ or MongoDB.
- Verify that the configuration files are correctly set up.
