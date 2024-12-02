package com.certidevs.controller;

import com.certidevs.model.Customer;
import com.certidevs.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

    public static final double IVA_21 = 1.21;

    private CustomerRepository customerRepository;

    // Métodos GET
    // Método que nos devuelva un saludo
    @GetMapping("welcome") // localhost:8080/welcome
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Bienvenido a un controlador de Spring");
    }

    // Método que captura un parámetro de la URL
    @GetMapping("user") // localhost:8080/user?name=Daniela
    public ResponseEntity<String> getUserName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok("Welcome user " + name);
    }

    // Métodos CRUD
    // Métodos GET
    // Método que nos devuelva todos los clientes
    @GetMapping("customers") // localhost:8080/
    @Operation(
            summary = "Obtener detalles de un recurso",
            description = "Esta operación devuelve información detallada sobre un recurso específico"
    )
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    // Método que devuelva un cliente por su id
    @GetMapping("customers/{id}") // localhost:8080/customers/2
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    return ResponseEntity.ok(customer);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método que nos devuelve clientes con salario incrementado en un 10%
    @GetMapping("customers-salary-modified") // localhost:8080/customers-salary-modified
    public ResponseEntity<List<Customer>> findAllWithSalaryModified() {
        var customers = customerRepository.findAll();

         customers.forEach(c -> c.setSalary(c.getSalary() * 1.10));
        // customers.forEach(c -> c.setSalary(c.getSalary() * IVA_21));

        return ResponseEntity.ok(customers);
    }

    // Método POST
    // Método para crear nuevo cliente
    @PostMapping("customers")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if(customer.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        customerRepository.save(customer); //obtiene un id

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PostMapping("customers/filter")
    public ResponseEntity<List<Customer>> findByFilter(@RequestBody Customer customer) {
        var customers = customerRepository.findAll(Example.of(customer));

        return ResponseEntity.ok(customers);
    }

    // Método PUT
    // Método para actualizar un cliente
    // Se actualiza el objeto completo y los campos que no se envíen se ponen a null
    @PutMapping("customers")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        if(customer.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        customerRepository.save(customer); //obtiene un id

        return ResponseEntity.ok(customer);
    }

    // Método PATCH
    // Actualización parcial de un cliente
    @PatchMapping(value = "customers/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Customer> partialUpdate(
            @PathVariable Long id, @RequestBody Customer customer
    ) {
        // Validación inicial
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Buscar el cliente por ID y realizar la actualización parcial
        return customerRepository.findById(id)
                // el map nos devuelve un Optional, hace que podamos o no tener un objeto
                // si este Optional está vacío se pasa el orElse

                // existingCustomer es un cliente que existe en base de datos
                .map(existingCustomer -> {
                    // customer es el cliente que viene en el body de la petición
                    if (customer.getName() != null) existingCustomer.setName(customer.getName());
                    if (customer.getEmail() != null) existingCustomer.setEmail(customer.getEmail());
                    if (customer.getPhone() != null) existingCustomer.setPhone(customer.getPhone());
                    if (customer.getAge() != null) existingCustomer.setAge(customer.getAge());
                    if (customer.getSalary() != null) existingCustomer.setSalary(customer.getSalary());
                    if (customer.getActive() != null) existingCustomer.setActive(customer.getActive());
                    customerRepository.save(existingCustomer);
                    return ResponseEntity.ok(existingCustomer); // 200
                })
                // Si no se encuentra el cliente, se devuelve un 404
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método DELETE
    // Método para eliminar un cliente
    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build(); //204
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    // Método para eliminar múltiples clientes cuyo ID esté en la lista
    @DeleteMapping("customers")
    public ResponseEntity<Void> deleteAll(@RequestBody List<Long> ids) {
        try {
            customerRepository.deleteAllByIdInBatch(ids);
            return ResponseEntity.noContent().build(); //204
        } catch (Exception e) {
            log.error("Error al eliminar un cliente", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error al eliminar un cliente");
        }
    }

}
