# ProductController (CRUD)

## 1. findAll: Trae todos los productos

model.addAttribute("productos", productRepository.findAll());

## 2. findById: Trae solo un producto por id 

model.addAttribute("producto", productRepository.findById(id).get())

## 3. getFormToCreate: Trae el formulario con un producto vacío para rellenarlo

* model.addAttribute("producto", new Product());
* model.addAttribute("fabricantes", manufacturerRepo.findAll());

## 4. getFormToUpdate: Trae el formulario con un producto completo para editarlo

* model.addAttribute("producto", productRepository.findById(id).get());
* model.addAttribute("fabricantes", manufacturerRepo.findAll());

## 5. save: Recibe un producto y lo guarda en base de datos

## 6. deleteById: Recibe un id de producto y borra el producto