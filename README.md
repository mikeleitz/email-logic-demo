# Overview

This project exposes a REST service to process email addresses.  There's one service that will count the distinct number of email address in a given list.

# Usage

## Build the project

This project uses some Java stream functionality that's only available in JDK 9.  Build and run it with Java 9 or higher.

Build with Gradle

```bash
./gradlew build
```

## Running the application

```bash
java -jar build/libs/email-logic-demo-0.0.1-SNAPSHOT.jar 
```

## Making requests

Send in list of emails specified in a file

```bash
curl -X POST -H "Content-Type: application/json" -d @./emails.json http://localhost:8080/email/unique
```

Send in a list from the command line

```bash
curl -d '[ "test.email@gmail.com", "test.email+spam@gmail.com", "test.email+spam@googlemail.com" ]' -H "Content-Type: application/json" -X POST http://localhost:8080/email/unique
```

