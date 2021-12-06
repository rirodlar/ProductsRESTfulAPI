package com.falabella.test.products.util;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ProductEntity;
import java.math.BigDecimal;


public class DataUtils {
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

    public static ProductEntity getMockProductEntity(String sku) {
        ProductEntity productEntity = ProductEntity.builder()
                .sku(sku)
                .price(BigDecimal.ONE)
                .name("Name")
                .brand("Brand")
                .imageUrl("https://s1.piq.land/2015/04/28/54oxzkYpKAtS26nzo3upKaTr_400x400.png")
                .build();

        return productEntity;
    }

    public static ProductResponseDto getMockProductResponseDto(String sku) {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .sku(sku)
                .price(BigDecimal.ONE)
                .name("Name")
                .brand("Brand")
                .imageUrl("https://s1.piq.land/2015/04/28/54oxzkYpKAtS26nzo3upKaTr_400x400.png")
                .build();

        return productResponseDto;
    }

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

//    public static ProductEntity getMockProductEntity(String sku) {
//        ProductResponseDto response = ProductResponseDto.builder()
//                .sku(sku)
//                .price(BigDecimal.TEN)
//                .name("name")
//                .brand("brand")
//                .imageUrl("https://s1.piq.land/2015/04/28/54oxzkYpKAtS26nzo3upKaTr_400x400.png")
//                .build();
//
//        return response;
//    }
}
