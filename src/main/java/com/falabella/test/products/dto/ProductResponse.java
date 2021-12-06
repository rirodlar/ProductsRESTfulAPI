package com.falabella.test.products.dto;

import com.falabella.test.products.entity.ImageProductEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductResponse {
    private String sku;
    private String name;
    private String brand;
    private String size;
    private BigDecimal price;
    private String imageUrl;

    private Set<ProductRequest.ImageDto> otherImages;
}
