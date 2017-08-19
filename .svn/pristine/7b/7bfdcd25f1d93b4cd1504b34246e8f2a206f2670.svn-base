:: implies comment in batch file
::liquibase running shell file
::liquibase --changeLogFile=./1.0.0/install.xml --username=postgres --password=Test1234 --url=jdbc:postgresql://localhost:5432/test --defaultSchemaName=kls --driver=org.postgresql.Driver update

::Running from properties file
: ./liquibase --changeLogFile=./1.0.0/install.xml --defaultsFile=liquibase.properties update

: ./liquibase --driver=org.postgresql.Driver --classpath=./lib --changeLogFile=/home/a1605/Desktop/kls.xml --url=jdbc:postgresql://192.168.1.193:5432/kls --defaultSchemaName=kls_schema --username=postgres --password=postgres generateChangeLog

: ./liquibase --changeLogFile=new.xml --defaultsFile=liquibase.properties update