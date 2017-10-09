# Registration and Collector service

## Installation
Install Java 8, Gradle and Mongo DB.

For linux and OSX, the exact processes are different, but neither 
is particularly hard.

### Linux (CentOS)
1. Java 8 - install this using yum; you may have to use the
   'alternatives' tool to set the correct version of Java. E.G.:
   1. sudo yum install java-1.8.0
   1. sudo alternatives --config java --> then choose java 1.8.0
   1. java -version --> to verify
1. Gradle - get this from http://gradle.org/gradle-download/.
   download the code and install unpack/install in a plce like
   /usr/local. Then set GRADLE_HOME in bashrc (or equiv) to
   point to /usr/local/gradle-<version> and add $GRADLE_HOME/bin
   to PATH. Also note that while 'alternatives' is great for most
   things, you must also set JAVA_HOME as well (and for gradle
   in particular). I used export JAVA_HOMR=/usr/lib/jvm/java-1.8.0.
1. Mongo db - install this using 'yum install mongodb'

### OSX (10.12, but older versions are mostly the same)
1. Java 8 - This is already part of the OS
1. Gradle - get this from _brew_ and install it using 
```brew install gradle```
You should get gradle 4.x or newer. 
1. Mongo db ```brew install mongodb```

### IDE notes
This code uses _Gradle_, _Spring Boot_ (1.5.7), and _lombok_. It's easiest to
set up your IDE so that these things are all configured. See the 
_reporter_ documentation for information on that configuration. For
IDEA, only _lombok_ needs to be added; for Eclipse use the _Eclipse Marketpalce_
to add the Sring STS and Gradle Buildship plugins.

## Configuration 
To configure the collector web application, you should set up the
_application.properties_ (in _src/main/resources/_) file with the
correct data:

```
mongo.db.name=harvester
mongo.db.server=localhost
server.port=8081
...
```

and then run the 'bootRun' command in the root project folder

```sh
$ gradle bootRun
```

It is better to run the application in a separate terminal using **nohup**.
To do this:

```sh
$ nohup gradle bootRun &
$ press any key if you want to store logs in nohup.out
```

The application will be started at `server.port.`

## API
All API methods:
* **GET** [/healthcheck]() - returns a string with the application version

* **GET** [/harvester/registration? serverUrl=http://xxx & ping=7 &
log=100]() - Register a new HyraxInstance. Check for that the server responds,
check the Reporter path and set up the ping and log-lines parameters. If
the Hyrax instance had been already registered, add new record and
make all previous records inactive. Returns created db record.

* **GET** [/harvester/registration? serverUrl=http://xxx &
reporterUrl=http://xxx & ping=7 & log=100]() - Same as previous call,
but configure a custom **reporterUrl**..

* **GET** [/harvester/allHyraxInstances]() - returns a list of all
**active** registered hyrax instances

* **GET** [/harvester/allHyraxInstances?onlyActive=false]() - returns
a  list of all **active** and **inactive** registered hyrax instances.

* **GET** [/harvester/logLines? hyraxInstanceName=http://xxx]() -
returns a list of all the log lines harvestered from a specific Hyrax
instance

* **GET** [/harvester/logLines/string? hyraxInstanceName=http://xxx]() - 
returns a string representation of the list of all log lines
harvestered from a specific Hyrax instance
<<<<<<< HEAD

=======
>>>>>>> branch 'master' of https://github.com/opendap/harvester.git
