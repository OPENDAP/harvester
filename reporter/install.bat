REM Copy all war files from directory where script is running to 
REM CATALINA_HOME directory if it is exist or to first argument directory
REM Example
REM install.bat  - will copy all wars to CATALINA_HOME 
REM install.bat /path/to/dir  - will copy all wars to /path/to/dir
REM 
IF NOT DEFINED CATALINA_HOME ( xcopy "*.war" %1 ) ELSE ( xcopy "*.war" %CATALINA_HOME%\webapps )
