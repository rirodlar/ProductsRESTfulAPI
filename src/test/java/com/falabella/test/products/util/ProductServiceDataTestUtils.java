package com.falabella.test.products.util;



import com.falabella.test.products.dto.ProductRequestDto;

import java.math.BigDecimal;

public class ProductServiceDataTestUtils {

    public static ProductRequestDto getMockProductRequest(String sku) {
        return ProductRequestDto.builder()
                .sku(sku)
                .brand("Name of the brand")
                .price(new BigDecimal(12))
                .size("Size of the product")
                .name("Short description of the product")
                .urlImage("https://dssdd.png")
                .build();

    }

}

