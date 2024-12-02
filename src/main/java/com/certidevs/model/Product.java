package com.certidevs.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;
    // asociaciones
    @ManyToOne
    private Manufacturer manufacturer;

}
