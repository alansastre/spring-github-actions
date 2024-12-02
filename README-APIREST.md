
# API REST

Una interfaz que permite la comunicación entre sistemas.
Para esto utiliza el protocolo HTTP para intercambiar datos en formato JSON principalmente.

# Métodos HTTP
- GET 
  - Devolvemos información. Puede ser un recurso o una colección de recursos.
- POST
    - Creamos un recurso. 
- PUT
    - Actualizamos un recurso. 
    - Es importante recordar que se actualiza todo el objeto. Y lo que no se envíe en el cuerpo de la petición se pone a null.
- PATCH
    - Actualización parcial del recurso. Lo que no enviamos en el cuerpo de la petición se mantiene igual, no se cambia a null.
- DELETE
  - Eliminamos un recurso o una colección de recursos.

# Anotaciones para crear la Api Rest
```java
@RestController
```

# Utilizar Postman y Swagger para probar la API
## POSTMAN
https://www.postman.com/
- Seleccionar tu sistema operativo y descargar la aplicación.
- No es necesario crear una cuenta para usar la aplicación.

## SWAGGER
https://springdoc.org/
```java
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```
- Si no funciona la versión 2.6.0 probar versiones anteriores.
- Para versiones anteriores del framework Spring Boot, la versión 2.2.0 va bien.
- Probarlo en estas dos urls
  - http://localhost:8080/swagger-ui.html
  - http://localhost:8080/swagger-ui/index.html