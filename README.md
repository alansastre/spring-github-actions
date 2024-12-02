


# ESQUEMA DE PASOS PARA TRABAJAR CON SPRING BOOT + MVC + SPRING DATA JPA + MYSQL


1. Crear y descargar proyecto en start.spring.io

https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.4&packaging=jar&jvmVersion=23&groupId=com.certidevs&artifactId=spring-003-mvc-mysql&name=spring-003-mvc-mysql&description=Demo%20project%20for%20Spring%20Boot&packageName=com.certidevs&dependencies=devtools,lombok,web,thymeleaf,data-jpa,mysql

Alternativa: si hay problemas con MySQL mejor usar H2.

2. Descomprimir proyecto zip en una carpeta que podamos abrir con IntelliJ IDEA

* File > Open > Seleccionar proyecto de Spring: spring-003-mvc-mysql

3. Crear clase Java en el paquete model: Customer
   * Agregar campos: id, name, nif, ...
   * Agregar anotaciones Lombok: @Getter, @Setter @Builder
   * Agregar JPA para MySQL: @Entity, @Id, @GeneratedValue

4. Crear interfaz Repository para el modelo: CustomerRepository
    * Plugin JPA Buddy permite la opción crear "Spring Data JPA Repository"

5. Crear datos desde Main (opcional)
    * context.getBean(CustomerRepository.class) y guardar objetos Customer en base de datos
    * Alternativa: crear los datos desde el controller o una clase nueva aparte

6. Crear una clase Controller en el paquete controller: CustomerController
   * findAll
   * findById
   * getFormToCreate
   * getFormToUpdate
   * save
   * deleteById
   * deleteAll

7. Crear vistas html con Thymeleaf:
   * customer-list.html
   * customer-detail.html
   * customer-form.html

8. Repetir todo el CRUD para el resto de modelos: Validamos en clase el CRUD con Alan
9. Asociaciones entre modelos:
    * Product
        * Manufacturer manufacturer (@ManyToOne)
    * Manufacturer
        * List<Product> products (@OneToMany)