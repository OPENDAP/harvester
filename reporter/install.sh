# Copy all war files from directory where script is running to 
# CATALINA_HOME directory if it is exist or to first argument directory
# Example
# install.sh  - will copy all wars to CATALINA_HOME 
# install.sh /path/to/dir  - will copy all wars to /path/to/dir
#
#!/bin/sh
if [ -z "$CATALINA_HOME" ]; then
    find . -name '*.war' | cpio -updm $1
else
	find . -name '*.war' | cpio -updm $CATALINA_HOME
fi  
