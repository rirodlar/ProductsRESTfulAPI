package com.falabella.test.products.util;

import com.falabella.test.products.dto.ProductResponse;
import com.falabella.test.products.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ProductResponse convertEntityToDto(ProductEntity product){
        return modelMapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> convertEntityToDto(List<ProductEntity> productList){
        return productList
                .stream()
                .map(product -> convertEntityToDto(product))
                .collect(Collectors.toList());
    }
}