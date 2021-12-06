package com.falabella.test.products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
    private String sku;
    private String name;
    private String brand;
    private String size;
    private BigDecimal price;
    private String imageUrl;

    private Set<ImageDto> otherImages = new HashSet<>();
}
