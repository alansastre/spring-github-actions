

Maven: 

* gestión de ciclo de vida de proyectos
* dependencias: selenium, spring, lombok, mysql, open api swagger....
* Reporting Tools: surefire, javadoc, jacoco, maven-site


* Todos los Plugins: https://maven.apache.org/plugins/index.html

* Javadoc plugin: https://maven.apache.org/plugins/maven-javadoc-plugin/usage.html
* Maven Site plugin: https://maven.apache.org/plugins/maven-site-plugin/


* Comprobar versión Java:
  * Abrir terminal en IntelliJ IDEA
  * java --version
  * java -version

* Ajustar variable entorno Java para que apunte a JDK 23
* Entrar en Variables de entorno:
* Donde pone Variables de usuario y Variables de Sistema:
* Buscar donde pone Path, dentro, hay que tener una sola ruta hacia el JDK:
* C:\Users\alanj\.jdks\temurin-23\bin
* Cerrar y volver a abrir IntelliJ IDEA

* Crear variable JAVA_HOME:
  * C:\Users\alanj\.jdks\temurin-23


* Alternativa:
* Abrir cmd
* set JAVA_HOME=C:\Users\alanj\.jdks\temurin-23
* set PATH=%JAVA_HOME%\bin;%PATH%

Ejecutar maven testing:

* Compilar: ./mvnw clean compile
* Testing: ./mvnw clean test
* Testing integración: ./mvnw clean verify
* Empaquetado: ./mvnw clean package
* Site: ./mvnw clean site: Genera un sitio web con documentación y reportes, depende de los plugins de reporting


## MAVEN PLUGINS

En build:

* spring-boot-maven-plugin (Ya estaba, lo añade spring boot)
* maven-compiler-plugin: añadimos la opción Lombok para que sepa cómo compilarlo
* maven-surefire-plugin: Testing unitario
* maven-failsafe-plugin: Testing integración
* maven-javadoc-plugin: Genera documentación de cobertura de testing
* maven-site-plugin: Genera un sitio web con documentación y reportes

En reporting: 

* maven-javadoc-plugin
* maven-surefire-report-plugin: Genera un reporte de testing unitario
* maven-failsafe-report-plugin: Genera un reporte de testing integración
* jacoco-maven-plugin: Genera un reporte de cobertura de testing

Ejecución: 

* ./mvnw clean site
* ./mvnw clean verify site
* ./mvnw clean verify site -Dmaven.test.failure.ignore=true

Abrir site:

* target/site/index.html

Ejecutar aplicación comprimida en jar:

* java -jar target/spring-003-mvc-mysql-0.0.1-SNAPSHOT.jar