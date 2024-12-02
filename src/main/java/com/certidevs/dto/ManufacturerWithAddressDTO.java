package com.certidevs.dto;

public record ManufacturerWithAddressDTO(
        Long manufacturerId,
        String manufacturerName,
        String city,
        Long productCount,
        Double totalProductPrice
) {
}
