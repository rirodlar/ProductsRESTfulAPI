package com.falabella.test.products.util;

import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ProductResponseDto convertEntityToDto(ProductEntity product) {
        return modelMapper.map(product, ProductResponseDto.class);
    }

//    public List<ProductResponseDto> convertEntityToDto(List<ProductEntity> productList) {
//        return productList
//                .stream()
//                .map(product -> convertEntityToDto(product))
//                .collect(Collectors.toList());
//    }
}
