# Flight Search API

## Overview

The **Amadeus Travel to Future Program** is an API developed for flight search purposes. With this API, you can perform
various flight searches and CRUD operations. Additionally, I have created a service called **FlightDataProvider** for
this
project. This service provides flight information to the **FlightSearchAPI** at a specific time every day. The flight
information is generated using the "Faker" API. Thanks to this FlightDataProvider service, users can perform searches
with various parameters.

## Technologies

- **Spring Boot**: Framework for creating web applications.
- **Spring Data JPA**: Framework for accessing relational databases.
- **Spring Security**: Framework for authentication and access-control. (I use JWT for authentication.)
- **Spring Scheduler**: Framework for scheduling tasks. (I use this for the FlightDataProvider service.)
- **Swagger**: Framework for documenting APIs.
- **Faker API**: Framework for generating fake data. (I use this for the FlightDataProvider service.)
- **PostgreSQL**: Relational database management system.
- **H2 DB**: I used for testing database.
- **Feign Client**: Framework for making HTTP requests. (I use this for the FlightDataProvider service.)

## Methodologies

- **Modular Structure**: I used a modular structure for this project. I created the following modules: FlightSearchAPI,
  FlightDataProvider, and FlightSearchAPI-Common.
- **Functional Programming**: I used functional programming for this project.
- **Layered Architecture**: I used a layered architecture for this project. I created the following layers: Controller,
  Service, Repository, and Model.
- **Test-Driven Development**: I used TDD for this project. I wrote unit tests for all the classes I created.
- **SOLID Principles**: I tried to follow the SOLID principles.
- **Design Patterns**: I used the Builder, Factory, and Singleton design patterns.
- **API Documentation**: I used Swagger for API documentation.
- **Unit Testing**: I used **JUnit** and **Mock** for unit testing.

## Unit Test Results
![unit_test_results](https://github.com/nuricanozturk01/Flight-Search-API/assets/62218588/f934d8df-7a6b-4f6f-b552-08b75f88ec3b)

## Database Design

### Airport Table

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | UUID      | Primary Key |
| city        | String    | City Name   |

### Flight Table

| Column Name          | Data Type | Description       |
|----------------------|-----------|-------------------|
| **id**               | UUID      | Primary Key       |
| departure_date       | LocalDate | departure date    |
| departure_time       | LocalTime | departure time    |
| return_date          | LocalDate | return date       |
| return_time          | LocalTime | return time       |
| price                | Double    | price             |
| arrival_airport_id   | UUID      | arrival airport   |
| departure_airport_id | UUID      | departure airport |
| return_flight_id     | UUID      | return flight     |

### Customer Table

| Column Name     | Data Type | Description     |
|-----------------|-----------|-----------------|
| **customer_id** | UUID      | Primary Key     |
| **username**    | String    | unique          |
| password        | String    | hashed password |
| **email**       | String    | unique          |
| first_name      | String    | first name      |
| middle_name     | String    | middle name     |
| last_name       | String    | last name       |

### authorities Table

| Column Name     | Data Type | Description |
|-----------------|-----------|-------------|
| **customer_id** | UUID      | Primary Key |
| role            | String    | role        |

## Features

- **FlightSearchService**:
    - You can search flights with various parameters.

- **FlightDataProvider**:
    - Provides flight information to the FlightSearchAPI at a specific time every day.

## AuthenticationController API Endpoints

| HTTP Method | Endpoint             | Description                                                                                                                                                                                                                                                                              |
|-------------|----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST        | `/api/auth/login`    | Handles user login process. Accepts `LoginDTO` containing user's login credentials and returns a `LoginResponseDTO` with login response data. In case of success, it returns a successful login response; in case of failure, it returns an error message.                               |
| POST        | `/api/auth/register` | Handles user registration process. Accepts `RegisterDTO` containing user's registration details and returns a `RegisterResponseDTO` with registration response data. In case of success, it returns a successful registration response; in case of failure, it returns an error message. |

## AdminController API Endpoints

| HTTP Method | Endpoint                      | Description                                                                                         |
|-------------|-------------------------------|-----------------------------------------------------------------------------------------------------|
| POST        | `/api/admin/create/flight`    | Used to create a new flight. Requires `CreateFlightDTO` with flight details for creation.           |
| POST        | `/api/admin/create/airport`   | Used to create or update an airport. Requires `CreateAirportDTO` with airport details.              |
| PUT         | `/api/admin/update/flight`    | Used to update an existing flight. Requires `UpdateFlightDTO` with updated flight details.          |
| PUT         | `/api/admin/update/airport`   | Used to update an existing airport. Requires `UpdateAirportDTO` with updated airport details.       |
| DELETE      | `/api/admin/delete/flight`    | Used to delete a flight by its unique ID. Requires `flightId` parameter as the flight's identifier. |
| DELETE      | `/api/admin/delete/airport`   | Used to delete an airport identified by a city name. Requires `city` parameter as the identifier.   |
| GET         | `/api/admin/find/flight/all`  | Used to retrieve a paginated list of all flights. Requires `page` parameter for pagination.         |
| GET         | `/api/admin/find/airport/all` | Used to retrieve a paginated list of all airports. Requires `page` parameter for pagination.        |

## FlightController API Endpoints

| HTTP Method | Endpoint                                             | Description                                                                                                                       |
|-------------|------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| GET         | `/api/flight/search/full-qualified`                  | Finds flights based on detailed criteria provided in `SearchFullQualifiedDTO`.                                                    |
| GET         | `/api/flight/search/by-airports`                     | Finds flights between two specified airports. Requires 'from' and 'to' city parameters, and an optional 'page' parameter.         |
| GET         | `/api/flight/search/airport-date-price-range`        | Finds flights based on airport, departure date, and price range, using `SearchFullQualifiedComparePriceDTO`.                      |
| GET         | `/api/flight/search/id`                              | Retrieves a flight by its unique ID. Requires 'id' parameter.                                                                     |
| GET         | `/api/flight/search/arrival-airport`                 | Finds flights arriving at a specified airport. Requires 'arrival' airport parameter and an optional 'page' parameter.             |
| GET         | `/api/flight/search/departure-airport`               | Finds flights departing from a specified airport. Requires 'from' parameter and an optional 'page' parameter.                     |
| GET         | `/api/flight/flights/airport-date-price-range`       | Finds flights based on airport, date, and price criteria, using `SearchFullQualifiedComparePriceDTO`.                             |
| GET         | `/api/flight/search/by-origin-destination-date`      | Finds flights between specific origin and destination on a specific date. Requires 'from', 'to', 'date', and an optional 'page'.  |
| GET         | `/api/flight/search/by-departure-date-range`         | Finds flights within a specified departure date range. Requires 'start', 'end', and an optional 'page' parameter.                 |
| GET         | `/api/flight/search/departure-airport-date-range`    | Finds flights from a specific airport within a date range. Requires 'from', 'start', 'end', and an optional 'page'.               |
| GET         | `/api/flight/search/price-range`                     | Finds flights within a specified price range. Requires 'min', 'max', and an optional 'page' parameter.                            |
| GET         | `/api/flight/search/departure-airport-specific-date` | Finds flights from a specific airport on a specific date. Requires 'from', 'date', and an optional 'page'.                        |
| GET         | `/api/flight/search/arrival-airport-specific-date`   | Finds flights arriving at a specific airport on a specific date. Requires 'arrival', 'date', and an optional 'page'.              |
| GET         | `/api/flight/search/from-to-specific-date`           | Finds flights between two cities on a specific date. Requires 'from', 'to', 'date', and an optional 'page'.                       |
| GET         | `/api/flight/search/cheapest-from-to-date-range`     | Finds the cheapest flights within a date range between two cities. Requires 'from', 'to', 'start', 'end', and an optional 'page'. |
| GET         | `/api/flight/search/city-date-range`                 | Finds flights related to a specific city within a date range. Requires 'city', 'start', 'end', and an optional 'page'.            |
