package com.falabella.test.products.repository;

import com.falabella.test.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {

    @Query("SELECT product from ProductEntity product INNER JOIN FETCH product.otherImages img")
    Set<ProductEntity> findProducts();
}
