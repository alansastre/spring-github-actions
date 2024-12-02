package com.certidevs.selenium.pom;

import com.certidevs.model.Manufacturer;
import com.certidevs.repository.ManufacturerRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Test de Selenium utilizando Page Object Model
 */
@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // puerto 8080
public class ManufacturerListTest {

    @Autowired
    private ManufacturerRepository manufacturerRepo;

    private WebDriver driver;
    private ManufacturerListPage page;

    @BeforeEach
    void setUp() {
        manufacturerRepo.deleteAllInBatch();

        driver = new ChromeDriver();
        driver.get("http://localhost:8080/manufacturers");
        driver.manage().window().maximize();
        page = new ManufacturerListPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void h1Test() {
        assertEquals("Listado fabricantes/Marcas", page.h1.getText());
    }

    @Test
    void createButton() {
        page.createButton.click();
        assertEquals(
                "http://localhost:8080/manufacturers/new",
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Comprobar que si no hay datos no sale la tabla, sale mensaje")
    void checkEmpty() {
        assertTrue(page.manufacturersEmpty.isDisplayed());
        assertThrows(
                NoSuchElementException.class,
                () -> page.manufacturersTable.isDisplayed()
        );
    }

    @Test
    @DisplayName("Comprobar que la tabla de fabricantes se muestra correctamente")
    void checkManufacturersTable() {
        insertManufacturers();
        assertTrue(page.manufacturersTable.isDisplayed());
        assertThrows(
                NoSuchElementException.class,
                () -> page.manufacturersEmpty.isDisplayed()
        );
    }

    @Test
    @DisplayName("Comprobar los datos de cada fabricante")
    void checkManufacturersData() {
        insertManufacturers();

        // Forma flexible comprobar: se obtienen todos los nombres de fabricantes sin importar el id:
        List<WebElement> names = page.getManufacturerNames();
        assertEquals(3, names.size());
        assertEquals("fabricante 1", names.getFirst().getText());
        assertEquals("fabricante 2", names.get(1).getText());
        names.forEach(name -> assertTrue(name.getText().startsWith("fabricante")));

        // Otra opción: traerlos de base de datos:
        List<Manufacturer> manufacturers = manufacturerRepo.findAll();
        Long manufacturerId = manufacturers.get(0).getId();
        assertEquals("fabricante 1", page.getManufacturerName(manufacturerId).getText());
    }

    @Test
    @DisplayName("Comprobar botón de Ver lleva a detalle de fabricante")
    void checkActionViewButton() {
        insertManufacturers();
        // CUIDADO: evitar usar ids estáticos porque en base de datos puede ser otro número
        // page.clickViewButton(1L);

        Manufacturer manufacturer = manufacturerRepo.findAll().getFirst();
        page.clickViewButton(manufacturer.getId());

        assertEquals(
                "http://localhost:8080/manufacturers/" + manufacturer.getId(),
                driver.getCurrentUrl()
        );
    }

    @Test
    @DisplayName("Comprobar todos los datos y acciones de un fabricante")
    void checkManufacturerAllData () {
        insertManufacturers();
        Manufacturer manufacturer = manufacturerRepo.findAll().getFirst();

        var manufacturerCard = page.getManufacturer(manufacturer.getId());

        // datos
        assertEquals(manufacturer.getName(), manufacturerCard.getName().getText());
        assertEquals("Año de fundación: " + manufacturer.getYear(), manufacturerCard.getYear().getText());
        assertEquals(manufacturer.getDescription(), manufacturerCard.getDescription().getText());
        assertEquals("http://localhost:8080/" + manufacturer.getImageUrl(), manufacturerCard.getImage().getAttribute("src"));

        // Accion Ver
        manufacturerCard.getViewButton().click();
        assertEquals("http://localhost:8080/manufacturers/" + manufacturer.getId(), driver.getCurrentUrl());
        driver.navigate().back();

        // Acción Editar
        manufacturerCard.getEditButton().click();
        assertEquals("http://localhost:8080/manufacturers/update/" + manufacturer.getId(), driver.getCurrentUrl());
        driver.navigate().back();

        // Acción Borrar
        // CUIDADO StaleElementReferenceException:Si quisieramos comprobar el nombre utilizando el objeto card hjabría que volver a leerlo para evitar problemas de desincronización
        manufacturerCard.getDeleteButton().click();
        assertThrows(NoSuchElementException.class, () -> page.getManufacturerName(manufacturer.getId()));
    }

    /**
     * Metodo para insertar fabricante demo y refrescar la pantalla
     */
    private void insertManufacturers() {
        manufacturerRepo.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").year(2019).description("man1").imageUrl("man1.png").build(),
                Manufacturer.builder().name("fabricante 2").year(2030).description("man2").imageUrl("man1.png").build(),
                Manufacturer.builder().name("fabricante 3").year(2015).description("man3").imageUrl("man1.png").build()
        ));

        driver.navigate().refresh();
    }


}
