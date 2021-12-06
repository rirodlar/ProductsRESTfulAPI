package com.falabella.test.products.controller;


import com.falabella.test.products.dto.ProductRequest;
import com.falabella.test.products.dto.ProductResponse;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.service.ProductService;
import com.falabella.test.products.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("product")
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;


    @PostMapping(value = "")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest payload){
        ProductResponse product = productService.createProduct(payload);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponseList = productService.findAllProducts();
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

}
