
https://sonarcloud.io/

Leer repositorio GitHub desde SonarCloud

https://sonarcloud.io/account/security


Generar token:

SPRING-GITHUB-ACTIONS

b87c8ae9f17ddc5703fbb6d30ac79f3361121cf0


Añadir token en GitHub:


https://github.com/alansastre/spring-github-actions/settings/secrets/actions/new

Nombre: SONAR_TOKEN
Valor: b87c8ae9f17ddc5703fbb6d30ac79f3361121cf0

Si está activado Automatic Analysis puede salir error: 

Error:  You are running CI analysis while Automatic Analysis is enabled. Please consider disabling one or the other.

Prueba.
