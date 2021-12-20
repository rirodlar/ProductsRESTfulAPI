package com.falabella.test.products.controller;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.exception.BadRequestException;
import com.falabella.test.products.service.ProductService;
import com.falabella.test.products.util.DataUtils;
import com.falabella.test.products.util.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegration {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private ProductService productService;

    @Test
    public void shouldCreateNewProductWhenCreateProductEndpointIsCalled() {
        ProductRequestDto productRequest = DataUtils.getMockProductRequest("FAL-2000267");

        Mockito.when(productService.createProduct(any()))
                .thenReturn(DataUtils.getMockProductResponseDto("FAL-2000267"));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);

        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product", entity, ProductResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @DisplayName("should Throw BadRequest When CreateProduct With Problem format Sku Endpoint Is Called")
    @Test
    public void shouldThrowBadRequestWhenCreateProductWithProblemFormatSkuEndpointIsCalled() {
        ProductRequestDto productRequest = DataUtils.getMockProductRequest("SEL#98");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product", entity, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("should Throw BadRequest When CreateProduct with Sku already exist Endpoint Is Called")
    @Test
    public void shouldThrowBadRequestWhenCreateProductWithAlreadySkuExistEndpointIsCalled() {
        ProductRequestDto productRequest = DataUtils.getMockProductRequest("FAL-2000267");
        Mockito.when(productService.createProduct(any()))
                .thenThrow(new BadRequestException(Message.SKU_ALREADY_EXIST));
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequest, header);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.postForEntity("/product", entity, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("should Return Product When FindByProduct Is Called")
    @Test
    public void shouldReturnProductWhenFindByProductIsCalled() {

        Mockito.when(productService.findProductBySku(anyString()))
                .thenReturn(DataUtils.getMockProductResponseDto("FAL-2000267"));

        String url = "/product/FAL-2000049";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.getForEntity(url, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("should Return Product When FindByAllProduct Is Called")
    @Test
    public void shouldReturnProductWhenFindByAllProductIsCalled() {

        Mockito.when(productService.findAllProducts())
                .thenReturn(List.of(
                        DataUtils.getMockProductResponseDto("FAL-2000267"),
                        DataUtils.getMockProductResponseDto("FAL-2000268")
                ));

        String url = "/product";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductResponseDto[]> response = testRestTemplate.getForEntity(url, ProductResponseDto[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("should UpdateProduct When Update Product Is Called")
    @Test
    public void shouldUpdateProductWhenUpdateProductIsCalled() {

        Mockito.when(productService.updateProductPartialBySku(anyString(), any()))
                .thenReturn(DataUtils.getMockProductResponseDto("FAL-2000267"));

        String url = "/product/FAL-2000267";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ProductResponseDto> response = testRestTemplate.getForEntity(url, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("should Return Page<Product> When FindByPage Is Called")
    @Test
    public void shouldReturnProductWhenFindByFilterPaginationIsCalled() throws Exception {
        Mockito.when(productService.findAllProducts(any(PageRequest.class)))
                .thenReturn(Mockito.mock(Page.class));

        mvc.perform(MockMvcRequestBuilders
                        .get("/product/page")
                        .queryParam("page", "1")
                        .queryParam("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }


    @Test
    public void shouldDeleteProductWhenDeleteProductEndpointIsCalled() {
        Mockito.when(productService.deleteProduct(anyString()))
                .thenReturn(true);
        testRestTemplate.delete("/product/1");

    }

    @Test
    public void shouldUpdateProductWhenUpdateProductEndpointIsCalled() {
        String sku = "FAL-2000056";
        ProductRequestDto productRequest = DataUtils.getMockProductRequest(sku);

        Mockito.when(productService.updateProductBySku(anyString(), any()))
                .thenReturn(DataUtils.getMockProductResponseDto(sku));

        testRestTemplate.put("/product/" + sku, productRequest);
    }

    @Test
    public void shouldUpdatePartialProductWhenUpdateProductEndpointIsCalled() {
        String sku = "FAL-2000056";
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Mockito.when(productService.updateProductPartialBySku(anyString(), any()))
                .thenReturn(DataUtils.getMockProductResponseDto(sku));

        Map<String, Object> requestMap = Map.of("brand", "newBrand");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, header);

        ResponseEntity<ProductResponseDto> response = testRestTemplate.exchange("/product/" + sku, HttpMethod.PATCH, entity, ProductResponseDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
