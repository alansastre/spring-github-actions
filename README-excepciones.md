

## GESTIÓN DE ERRORES

### PASO 1: crear el HTML

src/main/resources/templates/error.html

### PASO 2: crear una clase ControllerAdvice

Clase que captura excepciones y permite cargar mensajes concretos de error en el modelo para mostrarlos por la pantalla de error.

@ControllerAdvice

### PASO 3: agregar métodos para capturar excepciones

En la clase controller advice agregar métodos:

```java
@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
@ExceptionHandler(Exception.class)
public String handleException(Exception e, Model model) {
    model.addAttribute("message", e.getMessage());
    return "error";
}
```
