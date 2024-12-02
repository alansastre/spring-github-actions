package com.certidevs.repository;

import com.certidevs.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Vamos a declarar métodos para interactuar con la base de datos
y Spring Data JPA se encarga de traducir esos métodos a consulas SQL en base de datos.
 */
public interface ProductRepository extends JpaRepository<Product, Long> { }