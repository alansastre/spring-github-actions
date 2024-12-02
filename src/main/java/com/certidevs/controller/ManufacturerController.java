package com.certidevs.controller;

import com.certidevs.model.Address;
import com.certidevs.model.Manufacturer;
import com.certidevs.model.Product;
import com.certidevs.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class ManufacturerController {

    // Objeto inicializado e inyectado por Spring
    private ManufacturerRepository manufacturerRepo;

    @GetMapping("manufacturers")
    public String findAll(Model model) {
        model.addAttribute("manufacturers",
                manufacturerRepo.findAll());
        return "manufacturer-list"; // vista
    }

    @GetMapping("manufacturers/{id}")
    public String findById(@PathVariable Long id, Model model) {
        manufacturerRepo.findById(id)
                .ifPresent(manufacturer -> model.addAttribute("manufacturer", manufacturer));
        // Extra: podemos cargar más datos, por ejemplo products de este manufacturer
        return "manufacturer-detail";
    }

    @GetMapping("manufacturers/new")
    public String getFormToCreate(Model model) {
        Manufacturer manufacturer = new Manufacturer(); // Crear manufacturer
        manufacturer.setAddress(new Address()); // Inicializa una nueva dirección para el fabricante
        model.addAttribute("manufacturer", manufacturer);
        // model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturer-form";
    }

    @GetMapping("manufacturers/update/{id}")
    public String getFormToUpdate(Model model, @PathVariable Long id) {
        manufacturerRepo.findById(id)
                .ifPresentOrElse( // Si el fabricante existe, hace algo; si no, hace otra cosa
                        manufacturer -> { // Si el fabricante se encuentra
                            if(manufacturer.getAddress() == null) { // Verifica si la dirección del fabricante es nula
                                manufacturer.setAddress(new Address()); // Inicializa una nueva dirección si no existe
                            }
                            model.addAttribute("manufacturer", manufacturer); // Añade el fabricante al modelo para usarlo en la vista
                        },
                        () -> { // Si el fabricante no se encuentra
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fabricante no encontrado"); // Lanza un error 404 indicando que no se encontró
                        }
/*                        manufacturer -> model.addAttribute("manufacturer", manufacturer),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");;*/
                );
        return "manufacturer-form";
    }

    @PostMapping("manufacturers")
    public String save(@ModelAttribute Manufacturer manufacturer) {

        if (manufacturer.getId() == null) { // Verifica si el ID es nulo (crear nuevo fabricante)
            manufacturerRepo.save(manufacturer); // Guarda el nuevo fabricante en la base de datos

        } else { // Si el ID no es nulo (editar fabricante existente)
            manufacturerRepo.findById(manufacturer.getId()).ifPresent(manufacturerDB -> { // Busca el fabricante existente por ID
                BeanUtils.copyProperties(manufacturer, manufacturerDB, "id", "address"); // Copia propiedades excepto "id" y "address"

                // Actualizar la dirección asociada
                if(manufacturer.getAddress() != null) { // Verifica si se proporcionó una dirección nueva
                    if(manufacturerDB.getAddress() == null) { // Si no hay una dirección existente
                        manufacturerDB.setAddress(manufacturer.getAddress()); // Asigna la nueva dirección al fabricante
                    } else { // Si ya existe una dirección
                        BeanUtils.copyProperties(manufacturer.getAddress(), manufacturerDB.getAddress(), "id"); // Actualiza la dirección existente excepto el "id"
                    }
                }
                // BeanUtils.copyProperties(manufacturer, manufacturerDB);
                manufacturerRepo.save(manufacturerDB); // Guarda los cambios del fabricante en la base de datos
            });
        }
        return "redirect:/manufacturers"; // Redirige a la lista de fabricantes
    }

    @PostMapping("manufacturers2") // Maneja solicitudes POST a la URL "/manufacturers2"
    public String saveAndGoDetail(@ModelAttribute Manufacturer manufacturer) { // El método que recibe un objeto Manufacturer desde el formulario

        if (manufacturer.getId() == null) { // Verifica si el ID es nulo (crear nuevo)
            manufacturerRepo.save(manufacturer); // Guarda el nuevo fabricante en la base de datos

        } else { // Si el ID no es nulo (editar existente)
            manufacturerRepo.findById(manufacturer.getId()).ifPresent(manufacturerDB -> { // Busca el fabricante existente por ID
                BeanUtils.copyProperties(manufacturer, manufacturerDB, "id", "address"); // Copia propiedades excepto "id" y "address"

                // Actualizar la dirección asociada
                if(manufacturer.getAddress() != null) { // Verifica si hay una dirección nueva proporcionada
                    if(manufacturerDB.getAddress() == null) { // Si no hay una dirección existente
                        manufacturerDB.setAddress(manufacturer.getAddress()); // Asigna la nueva dirección
                    } else { // Si ya existe una dirección
                        BeanUtils.copyProperties(manufacturer.getAddress(), manufacturerDB.getAddress(), "id"); // Actualiza la dirección existente excepto el "id"
                    }
                }
                // BeanUtils.copyProperties(manufacturer, manufacturerDB);
                manufacturerRepo.save(manufacturerDB); // Guarda los cambios del fabricante en la base de datos
            });
        }

        return "redirect:/manufacturers/" + manufacturer.getId(); // Redirige a la pantalla detalle del fabricante
    }

    @GetMapping("manufacturers/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        manufacturerRepo.deleteById(id);
        return "redirect:/manufacturers";
    }

}
