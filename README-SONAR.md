
## CREAR CUENTA EN SONARCLOUD

https://sonarcloud.io/

Entrar con cuenta de Github.


## ANALIZAR PROYECTO EN SONARCLOUD

Pulsar en Analizar nuevo proyecto. (Lo tiene el owner del repositorio)

Leer repositorio GitHub desde SonarCloud.

## MÉTODO DE ANÁLISIS

Por defecto, se analiza automáticamente.

Necesitamos desactivar eso y analizar desde GitHub Actions.

https://sonarcloud.io/project/analysis_method?id=alansastre_spring-github-actions

Desactivar análisis automático.

Activar análisis GitHub Actions.

Al activarlo te muestra el token, se copia y pega en GitHub.

https://github.com/alansastre/spring-github-actions/settings/secrets/actions/new

Nombre: SONAR_TOKEN
Valor: xxxx


## AÑADIR PASO DE ANALISIS SONAR EN WORKFLOW YML DE GITHUB ACTIONS:

- name: Sonar Analysis
  env:
    SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
  run: |
    mvn sonar:sonar -Dsonar.projectKey=alansastre_spring-github-actions -Dsonar.organization=alansastre -Dsonar.host.url=https://sonarcloud.io


