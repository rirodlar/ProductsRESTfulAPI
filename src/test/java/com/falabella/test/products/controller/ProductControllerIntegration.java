package com.falabella.test.products.controller;



import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.service.ProductService;
import com.falabella.test.products.util.ControllerDataUtils;
import com.falabella.test.products.util.ProductServiceDataTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldCreateNewProductWhenCreateProductEndpointIsCalled() {
        ProductRequestDto productRequest = ProductServiceDataTestUtils.getMockProductRequest("FAL-2000267");


        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);

        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product",entity, ProductResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldThrowBadRequestWhenCreateProductEndpointIsCalled() {
        ProductRequestDto productRequest = ProductServiceDataTestUtils.getMockProductRequest("SEL#98");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product",entity, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnProductWhenFindByProductIsCalled() {

        Mockito.when(productService.findProductBySku(anyString()))
                .thenReturn(ControllerDataUtils.getMockProductEntity("FAL-2000267"));

        String url = "/product/FAL-2000049";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.getForEntity(url, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnProductWhenFindByAllProductIsCalled() {

        Mockito.when(productService.findAllProducts())
                .thenReturn(List.of(
                        ControllerDataUtils.getMockProductEntity("FAL-2000267"),
                        ControllerDataUtils.getMockProductEntity("FAL-2000267")
                ));

        String url = "/product";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductResponseDto[]> response = testRestTemplate.getForEntity(url, ProductResponseDto[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
