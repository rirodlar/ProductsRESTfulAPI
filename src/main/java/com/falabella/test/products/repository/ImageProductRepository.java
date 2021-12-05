package com.falabella.test.products.repository;

import com.falabella.test.products.entity.ImageProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProductEntity, Long> {

}
