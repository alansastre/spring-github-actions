

## PASO 1: AGREGAR DEPENDENCIA

En pom.xml:

```xml
 <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.26.0</version>
            <scope>test</scope>
</dependency>
```

## PASO 2: CREAR TEST INTEGRACIÓN COMPLETA

* Utilizamos tests de integración con base de datos.

Crear una clase de test por pantalla HTML:

* ProductListTest
* ProductDetailTest
* ProductFormTest

Ejemplo plantilla:

```java

import com.certidevs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductListTest {

    // inyectar repositorios
    @Autowired
    ProductRepository productRepository;

    WebDriver driver;

    @BeforeEach
        // Se ejecuta al comienzo de cada test
    void setUp() {
        productRepository.deleteAll();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/productos");
        driver.manage().window().maximize();
    }
}
```

Drivers:

* ChromeDriver <-- Estamos usando este
* FirefoxDriver
* OperaDriver
* EdgeDriver
* RemoteWebDriver: Lambda Test, BrowserStack, SauceLabs

Acciones del Driver:

* driver.get()
* driver.navigate().refresh()
* driver.navigate().back()
* driver.getCurrentUrl()
* driver.getTitle()
* driver.navigate().to("https://www.otro-ejemplo.com");

## CREAR IDS EN HTML

Para poder localizar elementos con selenium puede ser interesante añadir ids en los elementos a testear del HTML.

Por ejemplo:

* Botones
* Tablas
* Filas de tablas
* Campos de formulario

Tener ids únicos nos simplifican la localización de elementos desde Testing de Selenium.


## LOCALIZAR ELEMENTOS

* driver.findElement
* driver.findElements

Selectores:

* By.id()
* By.tagName()
* By.name()
* By.className()
* By.linkText()
* By.partialLinkText()
* By.cssSelector()
* By.xpath()

## ACCIONES SOBRE ELEMENTOS LOCALIZADOS

Una vez obtenemos un elemento podemos interactuar:

* webElement.getText()
* webElement.getAttribute()
* webElement.click()
* webElement.isDisplayed()

Estas acciones se combinan con aserciones de JUnit 5:

* assertTrue
* assertFalse
* assertEquals
* assertThrows por ejemplo cuando un elemento no se encuentra: NoSuchElementException de Selenium.


