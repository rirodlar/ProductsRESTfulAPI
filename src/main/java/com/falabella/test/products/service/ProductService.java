package com.falabella.test.products.service;


import com.falabella.test.products.dto.ProductRequest;
import com.falabella.test.products.dto.ProductResponse;
import com.falabella.test.products.entity.ImageProductEntity;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.repository.ImageProductRepository;
import com.falabella.test.products.repository.ProductRepository;
import com.falabella.test.products.util.EntityDtoConverter;
import com.falabella.test.products.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<ProductResponse> findAllProducts() {
        List<ProductResponse> productResponseList = new ArrayList<>();
        Set<ProductEntity> productEntityList = productRepository.findProducts();
        for (ProductEntity product : productEntityList) {
            productResponseList.add(entityDtoConverter.convertEntityToDto(product));
        }
        return productResponseList;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ProductResponse createProduct(ProductRequest productRequest) {

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productRequest.getSku());
        if (productEntityOptional.isPresent()) {
            throw new RuntimeException(Message.SKU_ALREADY_EXIST);
        }

        ProductEntity productEntity = ProductEntity.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .size(productRequest.getSize())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getUrlImage())
                .build();

        ProductEntity newProductEntity = productRepository.save(productEntity);
        if (!productRequest.getOtherImageSet().isEmpty()) {
            Set<ImageProductEntity> otherImageList = productRequest.getOtherImageSet().stream()
                    .map(img -> ImageProductEntity.builder()
                            .productEntity(newProductEntity)
                            .urlImage(img.getUrlImage()).build())
                    .collect(Collectors.toSet());
            imageProductRepository.saveAll(otherImageList);
        }
        return entityDtoConverter.convertEntityToDto(newProductEntity);
    }


}
