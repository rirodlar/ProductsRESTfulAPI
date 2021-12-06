package com.falabella.test.products.util;


import com.falabella.test.products.entity.ProductEntity;
import java.math.BigDecimal;


public class RepositoryDataUtils {
    public static ProductEntity getMockProductEntity(String sku, String name, String brand, BigDecimal price) {
        ProductEntity productEntity = ProductEntity.builder()
                .sku(sku)
                .price(price)
                .name(name)
                .brand(brand)
                .imageUrl("https://s1.piq.land/2015/04/28/54oxzkYpKAtS26nzo3upKaTr_400x400.png")
                .build();

        return productEntity;
    }
}
