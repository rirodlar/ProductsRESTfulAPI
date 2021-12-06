package com.falabella.test.products.controller;



import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.util.ProductServiceDataTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegration {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldCreateNewProductWhenCreateOrderEndpointIsCalled() {
        ProductRequestDto productRequest = ProductServiceDataTestUtils.getMockProductRequest("FAL-2000267");


        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);



        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product",entity, ProductResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ProductResponseDto bodyResponse = response.getBody();
        Assertions.assertNotNull(bodyResponse);
        Assertions.assertEquals(productRequest.getSku(), bodyResponse.getSku());
        Assertions.assertEquals(new BigDecimal(12), bodyResponse.getPrice());

    }
}
