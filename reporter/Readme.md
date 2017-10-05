# Reporter application
The _reporter_ is a web application that runs in a servlet engine and responds to requests
for information from a log file. Nominally, the log file is written by a web server
such as Hyrax. The log is assumed to be a text file where each line records information
about a single transaction. The contents of each line are unrestricted except that the _third_ 
token (space spearated) must be a date/time value that the software can parse. This is used 
to implement the _since=timestamp_ feature of the _log_ call.

## Log line format
The log line should match the following pattern (newline added for clarity):

```
[%X{host}] [%X{ident}] [%d{yyyy-MM-dd'T'HH:mm:ss.SSS Z}] [%8X{duration}] [%X{http_status}] [%8X{ID}] [%X{VERB}] [%X{resourceID}] [%X{query}] %n
```

## Prerequirements
To build the reporter war file you need:

1. Java 7 - see the Readme for the collector for information about 
   installing Java and gradle.
2. Gradle

### Configuration
The reporter needs to know where to read the logged data. Because of
various privacy issues, this system reads data from a log file that
has been 'sanitized' by the server itself. That is, while the server
writes a normal log file that includes IP addresses and UIDs, it also 
writes a log file for this system that excludes that information. We
call this an 'anonymous' log.

Because the format of the of the anonymous log might change over time,
the pattern used to parse each line can be configured. See the file
`logLinePattern.json` for this configuration. Note that this is not
something we expect users/installers to need to configure. It is a 
configuration option because the ideas of what we can gather in terms
of usage information may change over time and we would like to be
able to respond to those changes.

### Installation
## Installation
1. Clone this project
2. Configure **application.properties** file in _src/main/resources_
    * Set up the correct path to the Hyrax log file **hyrax.logfile.path**
    * Configure other parameters (reporter logging, Hyrax port number, et c.)
3. Run:

```sh
$ gradle war
```

4. The War file will be built in _build/libs/reporter.war_
5. Rename this file if you want to change the name of the reporter web application
6. Put this file to the **webapps** folder in the Tomcat (version 7+) servlet engine.
7. It will be automatically deployed. To check that the application is running, use the _healthcheck_ call:

```
http://{tomcat-address:tomcat-port}/{war-file-name}/healthcheck
```

## Configuration
TODO 
olfs.xml, how it's found. Explain the configuration files and options in more detail

## API
The reporter supports the following Web API methods:
* **GET** [/reporter/healthcheck]() - Returns the application version
* **GET** [/reporter/test]() - Can the service find the log file?
* **GET** [/reporter/defaultPing]() - Returns the default ping value from olfs.xml file or from properties.
* **GET** [/reporter/log]() - Returns all log lines from file.
* **GET** [/reporter/log?since=timestamp]() - Returns all log lines since **timestamp**.

TODO show the format of the _timestamp_.

