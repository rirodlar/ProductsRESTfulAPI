package com.falabella.test.products.service;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.exception.ProductNotFoundException;
import com.falabella.test.products.repository.ProductRepository;
import com.falabella.test.products.util.ExceptionMessageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("Should Throw Incorrect Exception When Order Items are Null")
    @Test
    public void shouldThrowIncorrectExceptionWhenOrderItemsAreNull() {
        ProductRequestDto productRequest = new ProductRequestDto();
        productRequest.setSku("FAL-123456789");

        ProductNotFoundException productNotFoundException = Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.findProductBySku(productRequest.getSku()));

        Assertions.assertEquals(ExceptionMessageEnum.PRODUCT_NOT_FOUND.getValue(), productNotFoundException.getMessage());
    }
}
