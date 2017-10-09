# Harvester
Metrics gathering for Hyrax and other web services

This software implements a simple system to poll web servers and read log file
entries. There are two parts to the software: a _reporter_ which is installed
alng with the web server (it is a servlet) and a _collector_ that periodically 
polls the known servers paired with reporter instances. The reporter reads 
information from a log file (either the web server's main log file or a special
one made just for this software) treating each line as a record. Each record 
is parsed using a regular expression that can be set as part of the report's 
configuration. Each parsed filed is named (the names are also set as part of
the reporter's configuration). Each line is returned as a set of field name and
value pairs in a simple JSON list.

See the two subdirectories here for the _reporter_ and _collector_ projects.

## Building the software
Each of the two subdirectories here contains a complete Java/Spring project
that is built using gradle. If you don't have gradle installed, use the gradle
wrapper build script ```gradlew```. To work on this software, it is easiest to 
make two projects, one for the _reporter_ and a spearate one for the_collector_.
You should set these up to build using gradle, include the lombok code and enable
them as spring-boot projects. Each directory has a Readme with more information.
