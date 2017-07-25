# Reporter application
### Pre requirements
To build war file you should have been installed on your machine:

1. Java 7 - see the Readme for the collector for inforamtion about 
   installing Java and gradle.
2. Gradle

### Installation

1. Clone last version of the project
2. Configure **application.properties** file. It is situated in _src/main/resources_
    * Set up the correct path to the log file **hyrax.logfile.path**
    * Configure other parameters (reporter logging, Hyrax port number, etc.)
3. Run command

```sh
$ gradle war
```

4. The War file will be built in _build/libs/reporter.war_
5. Rename file as you want to call your reporter application
6. Put this file in the **webapps** folder in Tomcat
7. It will be automatically deployed. To check application using 'healthcheck'

```
http://{tomcat-address:tomcat-port}/{war-file-name}/healthcheck
```

### API

All API methods:
* **GET** [/reporter/healthcheck]() - returns the application version
* **GET** [/reporter/defaultPing]() - returns the default ping value
    from the olfs.xml file or from the properties file (mentioned
    earlier).
* **GET** [/reporter/log]() - returns all log lines from file.
* **GET** [/reporter/log?since=timestamp]() - returns all log lines since **timestamp**.



