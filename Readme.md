Harvester application

To run server you should have been installed on your localhost:
1. Java 8
2. Gradle
3. Mongo db

To configure application to run you should set up application.properties file
with correct data:

mongo.db.name=harvester
mongo.db.server=localhost
server.port=8081

After that you need run in command line inside root project folder
    gradle bootRun

Application will be started at server.port.
