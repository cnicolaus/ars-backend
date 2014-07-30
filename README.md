Environment
===========

Erforderliche Software
----------------------

- Java 7
- Mysql 5.6
- Wildfly 8.0.0-Final

Einrichten der Datenbank
-------------------------
- User und Datenbanken erzeugen
```
create user 'ars'@'localhost' identified by 'ars';
create database ars;
create database ars_test;
grant all on ars.* to 'ars'@'localhost';
grant all on ars_test.* to 'ars'@'localhost';
```

- "ars-backend-jboss-dist\src\main\resources\schema.sql" in die Datenbank importieren
- "ars-backend-jboss-dist\src\main\resources\data.sql" in die Datenbank importieren (beinhaltet ein paar User, Flugzeuge, Flugzeugtypen und Lizenzen)

Vorbereiten des Wildfly
-----------------------

- Datei standalone.xml bearbeiten
 - Konfiguration Datasources
 
```
<datasource jndi-name="java:jboss/datasources/arsDS" pool-name="MySQLPool" enabled="true">
	<connection-url>jdbc:mysql://localhost:3306/ars</connection-url>
    <driver>mysql</driver>
    <pool>
      	<max-pool-size>30</max-pool-size>
    </pool>
    <security>
		<user-name>ars</user-name>
		<password>ars</password>
    </security>
</datasource>
<datasource jndi-name="java:jboss/datasources/ars_testDS" pool-name="MyPool" enabled="true">
	<connection-url>jdbc:mysql://localhost:3306/ars_test</connection-url>
	<driver>mysql</driver>
	<pool>
		<max-pool-size>30</max-pool-size>
	</pool>
	<security>
		<user-name>ars</user-name>
		<password>ars</password>
	</security>
</datasource>
```
 - Konfiguration Security-Domain
 
```
<security-domain name="ars-backend" cache-type="default">
    <authentication>
    <login-module code="Database" flag="required">
    <module-option name="dsJndiName" value="java:jboss/datasources/arsDS"/>
    <module-option name="principalsQuery" value="select password  from user where lower(username)= lower(?)"/>
    <module-option name="rolesQuery" value="select role, 'Roles' from user where lower(username)= lower(?)"/>
    <module-option name="unauthenticatedIdentity" value="GUEST"/>
    <module-option name="hashAlgorithm" value="SHA"/>
    <module-option name="hashUserPassword" value="true"/>
    </login-module>
    </authentication>
</security-domain>
```
- JDBC Treiber für Mysql (mysql-connector-java-5.1.29-bin.jar) in das Deployment-Verzeichnis des Wildfly kopieren

**Eine funktionierende standalone.xml befindet sich in Projekt ars-backend-jboss-dist**

Build
=====

Vorausetzungen
--------------

- Maven 3 musss installiert sein
- Datenbank muss gestartet und eingerichtet sein
- Wildfly muss gestartet und eingerichtet sein

Build starten
-------------

	mvn clean install 

ausführen.

 **Bei diesem Build werden alle Tests durchlaufen!**


Starten der Anwendung
=====================

"ars-backend-ear\target\ars-backend-1.0.0.ear" in das Deployment-Verzeichnis des Wildfly kopieren