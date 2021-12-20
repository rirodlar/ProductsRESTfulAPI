package com.falabella.test.products.repository;


import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.util.DataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.save(DataUtils.getMockProductEntity("FAL-2000067", "name1", "brand1", BigDecimal.ONE));
        productRepository.save(DataUtils.getMockProductEntity("FAL-1000001", "name1", "brand1", BigDecimal.TEN));
        productRepository.save(DataUtils.getMockProductEntity("FAL-1000002", "name1", "brand1", BigDecimal.TEN));
        productRepository.save(DataUtils.getMockProductEntity("FAL-1000003", "name1", "brand1", BigDecimal.ONE));
        productRepository.save(DataUtils.getMockProductEntity("FAL-1000004", "name1", "brand1", BigDecimal.ONE));
    }

    @Test
    public void shouldSaveProductWhenSaveIsCalled() {
        ProductEntity product = DataUtils.getMockProductEntity("FAL-1000000", "name", "brand", BigDecimal.TEN);

        ProductEntity newProduct = productRepository.save(product);

        Assertions.assertNotNull(newProduct.getSku());

        ProductEntity foundProduct = productRepository.findById(newProduct.getSku()).get();

        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(product.getSku(), foundProduct.getSku());
        Assertions.assertNotNull(newProduct.getSku());
        Assertions.assertNotNull(foundProduct.getBrand());
        Assertions.assertNotNull(foundProduct.getName());
        Assertions.assertNotNull(foundProduct.getPrice());
    }

    @Test
    public void shouldReturnAllProductsWhenFindAllIsCalled() {
        Iterable<ProductEntity> productEntityIterable = productRepository.findAll();
        Assertions.assertEquals(5, productEntityIterable.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void shouldReturnOneProductWhenFindByIdIsCalled() {
        Optional<ProductEntity> product = productRepository.findById("FAL-1000001");
        Assertions.assertEquals("FAL-1000001", product.get().getSku());
    }
}
