# PASOS PARA AGREGAR UNA ASOCIACIÓN MANY TO ONE

## Paso 1: 

Agregar atributo Manufacturer en la clase Product indicando
el tipo de asociación: 

```java
@ManyToOne
private Manufacturer manufacturer;
```

## Paso 2: 

Agregar selector de fabricantes en product-form.html

```html
<div>
    <label for="manufacturer">Selecciona un fabricante</label>
    <select id="manufacturer" th:field="*{manufacturer}">
        <option th:selected="${product.manufacturer == null}" value=""></option>
        <option
                th:each="manufacturer: ${manufacturers}"
                th:text="${manufacturer.name}"
                th:value="${manufacturer.id}"
        ></option>
    </select>
</div>
```

## Paso 3: 

En ProductController en los métodos de obtener formulario agregamos la lista de manufacturers al modelo

```java
model.addAttribute("manufacturers", manufacturerRepository.findAll());
```

## Paso 4: 

Asegurarse de que se guarda y actualiza el producto con el fabricante asociado.

```java 
BeanUtils.copyProperties(product, productoDB);
```

## Paso 5: Testing

* Se puede verificar manualmente la interfaz a guardar y actualizar un producto fabricante y comprobar que se queda guardado

* Selenium: comprobar eso mismo en la interfaz de usuario

## Paso 6: Agregar el fabricante en el resto de pantallas: product-list, product-detail