package com.falabella.test.products.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String sku;
    private String name;
    private String brand;
    private String size;
    private BigDecimal price;
    private String imageUrl;
}
