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

    GET (no parameters) returns a list of clients registered in the application
    POST add new client (query params: name, address_id, meter_id)

###/addresses

available methods:

    GET (no parameters) returns a list of addresses registered in the application
    POST add new address (query params: country, city, street, number)

###/meters

available methods:

    GET (no parameters) returns a list of clients registered in the application
    POST add new meter (query params: year, month, value)

###/meters/aggregate

available methods:

    GET (query parameters: year) returns the aggregate value of meter readings for that year


## Database access
1. Access this URL
http://localhost:8080/h2-console
   
2. Use the following settings to connect to the database: 
 
   JDBC URL: jdbc:h2:mem:testdb
   
   username : sa
   
   password : password
