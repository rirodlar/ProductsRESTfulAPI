package com.falabella.test.products.controller;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.exception.ViolationConstrainsProductException;
import com.falabella.test.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("product")
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;


    @PostMapping(value = "")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto payload,  UriComponentsBuilder uriComponentsBuilder){
        ProductResponseDto product = productService.createProduct(payload);
        UriComponents uriComponents = uriComponentsBuilder.path("/product/{sku}").buildAndExpand(product.getSku());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, uriComponents.toUriString());
        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);

    }

    @GetMapping(value = "")
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> productResponseDtoList = productService.findAllProducts();
        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{sku}")
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable String sku) {
        ProductResponseDto product = productService.findProductBySku(sku);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping(value = "/{sku}")
    public ResponseEntity updateProduct(@PathVariable String sku, @RequestBody Map<String, Object> changes) {
        try{
            ProductResponseDto productResponseDto =  productService.updateProductBySku(sku, changes);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        }catch (ViolationConstrainsProductException ex){
            return ResponseEntity.badRequest().body(ex.toString());
        }

    }
}
