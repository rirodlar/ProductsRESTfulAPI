package com.falabella.test.products.util;


import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ProductEntity;

import java.math.BigDecimal;


public class ControllerDataUtils {
    public static ProductResponseDto getMockProductEntity(String sku) {
        ProductResponseDto response = ProductResponseDto.builder()
                .sku(sku)
                .price(BigDecimal.TEN)
                .name("name")
                .brand("brand")
                .imageUrl("https://s1.piq.land/2015/04/28/54oxzkYpKAtS26nzo3upKaTr_400x400.png")
                .build();

        return response;
    }
}
