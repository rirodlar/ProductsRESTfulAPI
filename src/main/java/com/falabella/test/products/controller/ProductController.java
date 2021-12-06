package com.falabella.test.products.controller;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.exception.ViolationConstrainsProductException;
import com.falabella.test.products.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Creates an Product", notes = "This Operation creates a new Product.")
    @PostMapping(value = "product")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto payload) {
        ProductResponseDto product = productService.createProduct(payload);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve all existed products", notes = "This Operation returns all stored products.")
    @GetMapping(value = "product")
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> productResponseDtoList = productService.findAllProducts();
        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an Product", notes = "This Operation Delete a  Product.")
    @DeleteMapping(value = "product/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Find an Product By Sku", notes = "This Operation Find a  Product.")
    @GetMapping(value = "product/{sku}")
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable String sku) {
        ProductResponseDto product = productService.findProductBySku(sku);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an Product by Sku", notes = "This Operation Update a  Product.")
    @PatchMapping(value = "product/{sku}")
    public ResponseEntity updateProduct(@PathVariable String sku, @RequestBody Map<String, Object> changes) {
        try {
            ProductResponseDto productResponseDto = productService.updateProductBySku(sku, changes);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (ViolationConstrainsProductException ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
    }
}
