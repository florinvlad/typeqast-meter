# typeqast-meter

## Installation and startup

Prerequisites for running the application:

1. Install Java 8

2. Install Maven 3

3. Install Git

4. Add JAVA_HOME and MVN_HOME to environment variables, and git executable folder, maven executable folder to Path


After you have installed the required software open a command line terminal and go to a folder where 
you want to download the project (Example: D:\Projects) . Alternatively if you have installed an IDE (such as Eclipse or IntelliJ) you can import the project directly there.

For the command line terminal version do the following steps:
1. Go to D:\Projects
2. run the following command there :
git clone https://github.com/florinvlad/typeqast-meter
3. After the project finished downloading go to the project folder (cd typecast-meter) and run the following command (without the quotes):
"mvn spring-boot:run"
   
## API capabilities

###REST Endpoints:

###/clients

available methods: 

    GET returns a list of clients registered in the application
       (no parameters) 

    POST add new client 
         sample json body: 
            {
            "name": "client_name1"
            }

    PUT update client
         sample json body: 
            {
            "id":1
            "name": "client_name1"
            }


###/addresses

available methods:

    GET returns a list of addresses registered in the application
         request parameters : id (optional)

    POST add new address for existing client 
      request parameters : client_id (mandatory)
      sample json body:
      {
      "country": "country1",
      "city": "city1",
      "street": "street1",
      "number": 1
      }

    PUT update existing client address
      request parameters : client_id (mandatory)
      sample json body:
      {
      "id": 4,
      "country": "country1",
      "city": "city1",
      "street": "street1",
      "number": 1
      }

###/meters

available methods:

    GET returns a list of meters registered in the application
         request parameters : id (optional)

    POST add new meter for existing client
      request parameters : client_id (mandatory)
      sample json body:
      {
      "name":"newmeter"
      }

    PUT update existing client meter
      request parameters : client_id (mandatory)
      sample json body:
      {
      "id": 17,
      "name":"newmeter"
      }


(query params: year, month, value)

###/readings

available methods:

    GET returns a list of readings registered in the application
         (no parameters)

    POST add new reading for an existing meter
         request parameters : meter_id (mandatory)
         sample json body:
         {
         "id": 10,
         "year": 2001,
         "month": "JANUARY",
         "value": 1
         }

###/meters/aggregate

available methods:

    GET returns the aggregate value of meter readings for that year
         request parameters: year (mandatory) , meter_id (mandatory)

You can download a collection of postman requests at the following URL:
https://www.getpostman.com/collections/c0a9f011c1bc405102eb

## Database access
1. Access this URL
http://localhost:8080/h2-console
   
2. Use the following settings to connect to the database: 
 
   JDBC URL: jdbc:h2:mem:testdb
   
   username : sa
   
   password : password
