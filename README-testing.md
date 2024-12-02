

## 1. TESTING UNITARIO EN SPRING

Unitario porque probamos unidades (métodos). No se ejecuta Spring, simplemente se prueba el código Java.

Preparar el test: 

* @ExtendWith(MockitoExtension.class)
* @InjectMocks sobre la clase a testear
* @Mock sobre las dependencias a crear Mocks

Pasos de cada test:

1. Configurar respuestas mocks (si las hay)
2. Ejecutar el método a testear
3. Asserciones y verificaciones

Uso mocks:

* when()
* thenReturn()
* thenAnswer()
* doAnswer()
* doThrow()
* verify()
* never()
* any()

Ejecutar testing con cobertura coverage

* Run ... with Coverage

Cobertura representa la cantidad de código alcanzado por las pruebas. El objetivo es conseguir una cobertura alta por ejemplo del 80 %.


Objetivos:

* Crear una app web con spring mvc
* Testear la app web



## 2. TESTING INTEGRACIÓN PARCIAL

Podemos crear un test de integración parcial integrando solo aquellas partes de Spring que nos interesen.

Por ejemplo para testear un controlador como ManufacturerController, nos basta con la vista, el controlador y el contexto de Spring.

Realmente no necesitaría el repositorio, otros controladores distintos, servicios...


* @WebMvcTest(ManufacturerController.class): logramos cargar solamente lo mínimo imprescindible de Spring para probar este controlador.

* @MockBean ManufacturerRepository para crear mocks de repositorios o servicios

* @Autowired MockMvc: inyectamos una utilidad de testing que permite lanzar peticiones HTTP
  * mockMvc.perform para lanzar peticiones
  * andExpect para hacer verificaciones de la respuesta obtenida

Ventajas:

* Es más rápido que un test de integración completo
* Más enfocado en probar un solo controlador

### Imports para testing con MockMvc:

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

Opciones:

* Repasar el ManufacturerControllerPITest depurando
* ProductControllerPITest
* Proyectos
* Dudas


### Ejemplo: 

* status, view, model

```java
    @Test
    void findById() throws Exception {
        var manufacturer = Manufacturer.builder().id(1L).name("MSI").build();
        when(manufacturerRepository.findById(1L))
                .thenReturn(Optional.of(manufacturer));

        mockMvc.perform(get("/manufacturers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("manufacturer-detail"))
                .andExpect(model().attributeExists("manufacturer"));

        verify(manufacturerRepository).findById(1L);
    }
```


## 3. TESTING INTEGRACIÓN CON BASE DE DATOS

1. Agregar anotaciones en la clase de test:

@SpringBootTest
@AutoConfigureMockMvc

2. Utilidades de ayuda:

* MockMvc para lanzar las peticiones HTTP
* Repositorios para preparar datos para los test
* EntityManager (opcional)
* Inserción de script SQL (opcional)

3. Test probar los controladores, se invocan todas las capas reales: controlador, servicio, repositorio, base de datos...

### Base de datos

#### Opción 1: 

utilizar la propia base de datos de desarrollo, MySQL.

Por tanto no haríamos nada ni configuramos nada, se carga automáticamente en base a src/java/resources/application.properties.

#### Opción 2: 

configuración de base de datos específica para testing, por ejemplo base de datos en memoria H2. 

Independiente de MySQL.

pom.xml:

```xml
<dependency>
<groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
<scope>test</scope>
</dependency>
```

src/test/resources/application.properties

```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=true
```

IMPORTANTE, MUCHO CUIDADO:

* Hay que tener cuidado con los nombres de los atributos que usamos en nuestros modelos porque ciertas palabras están reservadas en bases de datos como H2, PostgreSQL como por ejemplo: year, users
* Solución: @Column(name = "manufacturer_year")


#### Opción 3: Testcontainers

https://testcontainers.com/

https://certidevs.com/tutorial-bash-shell-instalar-ubuntu-en-windows-con-wsl

https://docs.docker.com/desktop/install/windows-install/

https://learn.microsoft.com/en-us/windows/wsl/install



Opcional LAMP Javier: https://javguerra.github.io/blog/docker-lamp/