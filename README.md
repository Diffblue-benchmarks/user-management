# User Management Service

## Description

This service manages user data. This service exposes REST API's to receive CRUD requests for users.

## How it works
User management service has its own PostgreSQL(postgres) database and uses Google Cloud Pub/Sub to publish events. 
It publishes events to its specified topic on any change of the state. Every event contains `uid` (User Id) to identify 
the user. The events do not contain any sensitive information. All personal user information is stored in service's 
postgres database. 

## Service Dependencies
* Java 11
* Maven (alternatively you can use maven wrapper from within the repository)
* Google Pub/Sub Account
* Postgres Database

## How to run
* Create a GCP service account
* Download the service json key
* Assign appropriate roles to the service account to be able to publish messages on Google Cloud Pub/Sub
* Once the file is downloaded, export the file location as a runtime environment variable
   
    ```bash
    GOOGLE_APPLICATION_CREDENTIALS=/path/to/service-account.json
    ```

    **Important** The above environment variable must be available in application runtime for the service to start.
* Create a topic on Pub/Sub for the service to publish events
* Configure the topic name in `application.yaml` file under `src\main\resources`
    ```yaml
        user:
          events:
            topic: hello
    ```
* Run Postgres on your local machine. Alternatively, you can use docker to run postgres on you local machine

    ```bash
    docker run --name some-postgres -e POSTGRES_DB=postgres -e POSTGRES_USER=root -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
    ```

* Set up `application.yaml` file under `src\main\resources` with postgres credentials

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/postgres
        username: root
        password: password
    ```

* Perform clean build

    ```bash
    mvn clean package
    # Or 
    ./mvnw clean package
    ```

* Run the application

    ```bash
    export GOOGLE_APPLICATION_CREDENTIALS=/path/to/service-account.json && java -jar target/user-management-*.jar
    ```

### Guides

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Google Cloud Pub/Sub](https://cloud.google.com/pubsub/docs/how-to)
* [PubSub emulator for local development](https://cloud.google.com/pubsub/docs/emulator#pubsub-emulator-java)

