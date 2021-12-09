package com.falabella.test.products.service;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.exception.ProductNotFoundException;
import com.falabella.test.products.exception.ViolationConstrainsProductException;
import com.falabella.test.products.repository.ProductRepository;
import com.falabella.test.products.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EntityDtoConverter entityDtoConverter;



    @DisplayName("Should Return List ProductEntity When FindAllProduct Is Called")
    @Test
    public void shouldReturnListProductEntityWhenFindAllProductIsCalled() {
        Mockito.when(productRepository.findAllProductWithImage())
                .thenReturn(Set.of(DataUtils.getMockProductEntity("FAL-123456789")));

        Mockito.when(entityDtoConverter.convertEntityToDto(Mockito.any(ProductEntity.class)))
                .thenReturn(DataUtils.getMockProductResponseDto("FAL-123456789"));

        Set<ProductEntity> productEntityList = productRepository.findAllProductWithImage();

        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(productEntityList.size(),1);
    }

    @DisplayName("Should Throw Not Found Exception When Sku not exit")
    @Test
    public void shouldThrowNotFoundExceptionWhenSkuNotExit() {
        ProductRequestDto productRequest = new ProductRequestDto();
        productRequest.setSku("FAL-123456789");

        ProductNotFoundException productNotFoundException = Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.findProductBySku(productRequest.getSku()));

        Assertions.assertEquals(ExceptionMessageEnum.PRODUCT_NOT_FOUND.getValue(), productNotFoundException.getMessage());
    }

    @Test
    @DisplayName("should Return ProductEntity When Create Product Is Called")
    public void shouldReturnProductEntityWhenCreateProductIsCalled() {
        ProductRequestDto request = DataUtils.getMockProductRequest("FAL-123456789");

        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(entityDtoConverter.convertEntityToDto(Mockito.any(ProductEntity.class)))
                .thenReturn(DataUtils.getMockProductResponseDto("FAL-123456789"));

        ProductResponseDto productResponseDto =  productService.createProduct(request);
        Assertions.assertNotNull(productResponseDto);
        Assertions.assertEquals(productResponseDto.getSku(), request.getSku());
        Mockito.verify(productRepository).save(any());
    }

    @Test
    @DisplayName("should Return ProductEntity Update When Update Product Is Called")
    public void shouldReturnProductEntityUpdateWhenUpdateProductIsCalled() throws ViolationConstrainsProductException {
        String sku = "FAL-2000068";
        String newBrand ="NewBrand";
        String newName ="NewName";
        String newSize ="NewSize";
        BigDecimal newPrice = BigDecimal.TEN;
        String newImageUrl ="http://www.falalla/image.jp";
        ProductEntity productEntity = DataUtils.getMockProductEntity(sku, "Name", "Brand", BigDecimal.ONE);

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(productEntity));


        Mockito.when(productRepository.saveAndFlush(Mockito.any(ProductEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);


        Mockito.when(entityDtoConverter.convertEntityToDto(Mockito.any(ProductEntity.class)))
                .thenReturn(DataUtils.getMockProductResponseDto(sku));

        ProductResponseDto productResponseDto = productService
                .updateProductBySku(sku, Map.of("brand", newBrand, "name", newName, "price", newPrice, "imageUrl",newImageUrl, "size", newSize));
        Assertions.assertNotNull(productResponseDto);
        Mockito.verify(productRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("should Throw Violation Constraint Exception When Update Product Is Called")
    public void shouldThrowViolationConstraintExceptionWhenUpdateProductIsCalled() throws ViolationConstrainsProductException {
        String sku = "FAL-2000068";
        String brandWithError ="B";
        ProductEntity productEntity = DataUtils.getMockProductEntity(sku, "Name", "Brand", BigDecimal.ONE);

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(productEntity));


        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);


        Mockito.when(entityDtoConverter.convertEntityToDto(Mockito.any(ProductEntity.class)))
                .thenReturn(DataUtils.getMockProductResponseDto(sku));


        ViolationConstrainsProductException violationConstrainsProductException = Assertions.assertThrows(ViolationConstrainsProductException.class,
                () -> productService.updateProductBySku(sku, Map.of("brand", brandWithError)));

        Assertions.assertNotNull(violationConstrainsProductException);
    }

}
