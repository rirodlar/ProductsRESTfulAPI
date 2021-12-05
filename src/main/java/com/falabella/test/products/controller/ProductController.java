package com.falabella.test.products.controller;


import com.falabella.test.products.dto.ProductRequest;
import com.falabella.test.products.dto.ProductResponse;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.service.ProductService;
import com.falabella.test.products.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;


    @PostMapping(value = "product/create")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest payload){
        ProductEntity productEntity = productService.createProduct(payload);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(productEntity), HttpStatus.CREATED);
    }


}
