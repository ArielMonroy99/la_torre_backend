#!/bin/bash
echo "Compiling the project..."
mvn clean compile

echo "Cleaning database"
mvn -Dflyway.cleanDisabled=false flyway:clean

echo "Running Flyway migrations..."
mvn flyway:migrate -X

echo "Flyway info"
mvn flyway:info

echo "Done!"
