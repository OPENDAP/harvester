# Reporter application
### Pre requirements
To build war file you should have been installed on your machine:

1. Java
2. Gradle

### Installation

1. Clone last version of the project
2. Configure **application.properties** file. It is situated in _src/main/resources_
    * Set up correct path to log file **hyrax.logfile.path**
3. Run command
```sh
$ gradle war
```
4. War file will be build in _build/libs/reporter-0.0.1-SNAPSHOT.war_
5. Rename file as you want to call your reporter application
6. Put this file to **webapps** folder in your Tomcat 7+
7. It will be automatically deployed. To check application running call
```
http://{tomcat-address:tomcat-port}/{war-file-name}/healthcheck
```

### API

All API methods:
* **GET** [/healthcheck]() - returns string with application version
* **GET** [/reporter/log]() - returns all log lines from file.
* **GET** [/reporter/log?since=timestamp]() - returns all log lines since date **timestamp**.



