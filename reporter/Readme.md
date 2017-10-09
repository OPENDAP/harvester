
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

1. Java 8 
1. Gradle (Add Gradle support - Buildship Gradle Integration - Eclipse using the market place)
1. Spring and Spring boot (install the Spring Tools - STS - in Eclipse)
1. lombok (https://projectlombok.org/ - installs in Eclipse and IDEA)

## Builing and Installing the service
1. Clone this project
2. Configure **application.properties** file in _src/main/resources_
    * Set up the correct path to the Hyrax log file **hyrax.logfile.path**
    * Configure other parameters (reporter logging, Hyrax port number, et c.)
3. Build the Web ARchive file using ```gradle wrapper```:

```sh
$ gradlew war
```

or, if you have gradle 4.2.1 or greater installed, 

```sh
$gradle war
```

4. The War file will be built in _build/libs/reporter.war_
5. Rename this file if you want to change the name of the reporter web application
6. Put this file to the **webapps** folder in the Tomcat (version 7+) servlet engine.
7. It will be automatically deployed. To check that the application is running, use the _healthcheck_ call:

```
http://{tomcat-address:tomcat-port}/{war-file-name}/healthcheck
```

## Configuration
The _reporter_ can be customized by changing several different parameters. By default, the
values of the parameters are compiled into the service. The default values can be changed 
by editing the reporter/resources/application.properties. These parameters are:
1. server.port=8080
1. reporter.version = 0.91
1. logging.file=reporter.log
1. logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
and:
1. hyrax.logfile.path = /etc/olfs/logs/AnonymousAccess.log
1. hyrax.default.ping = 3600
1. # logfile.pattern.path = logLinePattern.json # use this to override the pattern below.
1. logfile.pattern.names = host;sessionId;localDateTime;duration;httpStatus;requestId;httpVerb;resourceId;query
1. logfile.pattern.regexp = \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\] \\[(.*)\\]

The last five parameters can also be set from within the ```olfs.xml```
file.

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

The last two parameters can also be set using the _olfs.xml_ configuration file. To do so,
include the following XML elements in that file:

```xml
<LogReporter>
    <HyraxLogfilePath>
        /etc/olfs/logs/AnonymousAccess.log
    <HyraxLogfilePath>
    <DefaultPing>
        3600
    </DefaultPing>
</LogReporter>
```

The reporter will look for the OLFS.xml file in the directory named by the environment
variable _OLFS_CONFIG_DIR_. If that variable is not set, it will look in the default 
location, _/etc/olfs/_. If neither of those locations holds the configuration file, the
compiled parameter values are used.

This service reads a special log file written by the OLFS called the _Anonymous Log_. That 
log file is not written by default. To configure the OLFS to write to the Anonymous Log, add
the following to the OLFS's logback.xml file (found in _webapps/opendap/WEB-INF_).

```xml
<!-- Access/Performance Logging -->
<logger name="HyraxAccess" level="all">
    <appender-ref ref="HyraxAccessLog"/>
    <appender-ref ref="AnonymousAccessLog"/>
</logger>
```

## API
The reporter supports the following Web API methods:
* **GET** [/reporter/healthcheck]() - Returns the application version
* **GET** [/reporter/test]() - Can the service find the log file?
* **GET** [/reporter/defaultPing]() - Returns the default ping value from olfs.xml file or from properties.
* **GET** [/reporter/log]() - Returns all log lines from file.
* **GET** [/reporter/log?since=timestamp]() - Returns all log lines since **timestamp**.

TODO show the format of the _timestamp_.

