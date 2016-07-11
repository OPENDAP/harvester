# Reporter application
### Pre requirements
To build war file you should have been installed on your machine:

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

1. Clone last version of the project
2. Configure **application.properties** file. It is situated in _src/main/resources_
    * Set up correct path to log file **hyrax.logfile.path**
    * Configure other parameters (reporter logging, Hyrax port number, etc.)
3. Run command

```sh
$ gradle war
```

4. War file will be build in _build/libs/reporter.war_
5. Rename file as you want to call your reporter application
6. Put this file to **webapps** folder in your Tomcat 7+
7. It will be automatically deployed. To check application running call

```
http://{tomcat-address:tomcat-port}/{war-file-name}/healthcheck
```

### API

All API methods:
* **GET** [/reporter/healthcheck]() - returns string with application version
* **GET** [/defaultPing]() - returns default ping value from olfs.xml file or from properties.
* **GET** [/reporter/log]() - returns all log lines from file.
* **GET** [/reporter/log?since=timestamp]() - returns all log lines since date **timestamp**.



