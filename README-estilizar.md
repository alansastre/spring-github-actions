## ESTILIZACIÓN

### Dependencias

1. Ir a https://mvnrepository.com/
  - Buscar dependencias de:
     - Bootstrap 5.3.3
     - Webjars locator 0.52
     - Font Awesome 6.5.2
  - Añadir dependencias a `pom.xml` y **RECARGAR**
    
2. Enlazar links (en head) y scripts (en body) a **CADA** HTML
   - `<link rel="stylesheet" th:href="@{/webjars/bootstrap/css/bootstrap.min.css}">`
   - `<link rel="stylesheet" th:href="@{/webjars/font-awesome/css/all.min.css}">`
   - `<script th:src="@{/webjars/bootstrap/js/bootstrap.bundle.min.js}"></script>`
   - `<script th:src="@{/webjars/font-awesome/js/all.min.js}"></script>`
   
3. Utilizar clases para estilizar en las etiquetas
   - Contenedor: `container`
   - Botón: `btn`
   - Colores: `success` (verde), `danger`(rojo), `warning` (amarillo),...
   - Márgenes: `mt-2` (margen arriba), `mb-2` (margen abajo), `ms-2` (izquierda), `me-2` (derecha)
   - Formularios: `form-label`, `form-control`, `form-check-input`, `form-select`
   
4. Título dinámico:
   `th:text="${product.id != null} ? 'Editar producto' : 'Crear producto'"`

5. Recargar aplicación automáticamente
   1. File>Settings>Build, Execution, Deployment>Build, Execution, Deployment y marcar checkbox `Build project automatically`
   2. File>Settings>Advanced Settings y marcar checkbox llamada `Allow auto-make to start even if developed application is currently running`

6. Fragments de Thymeleaf
   - Crear archivo HTML con código que queremos reutilizar y añadir al código `th:fragment="nav1"`
   - En el archivo HTML en donde queremos insertar el código, añadir `<div th:replace="~{navbar :: nav1}"></div>`
   - `navbar` hace referencia al nombre del archivo HTML donde se encuentra el fragment
   - `nav1` hace referencia al nombre del fragmento
   
7. Barra de navegación
   - Clase `navbar`
   - Hacerla responsive con toggle
   - Tener cuidado con el color scheme y el contraste `data-bs-theme="dark"`
   
8. Footer
   - Tener cuidado con el color scheme y el contraste