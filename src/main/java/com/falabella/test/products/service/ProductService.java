package com.falabella.test.products.service;


import com.falabella.test.products.dto.ProductRequestDto;
import com.falabella.test.products.dto.ProductResponseDto;
import com.falabella.test.products.entity.ImageProductEntity;
import com.falabella.test.products.entity.ProductEntity;
import com.falabella.test.products.exception.BadRequestException;
import com.falabella.test.products.exception.ProductNotFoundException;
import com.falabella.test.products.repository.ImageProductRepository;
import com.falabella.test.products.repository.ProductRepository;
import com.falabella.test.products.util.EntityDtoConverter;
import com.falabella.test.products.util.ExceptionMessageEnum;
import com.falabella.test.products.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.*;
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

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProducts() {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        Set<ProductEntity> productEntityList = productRepository.findAllProductWithImage();
        for (ProductEntity product : productEntityList) {
            productResponseDtoList.add(entityDtoConverter.convertEntityToDto(product));
        }
        return productResponseDtoList;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntityList = productRepository.findAll(pageable);
        if(productEntityList != null) {
            Page<ProductResponseDto> productResponsePageDto = productEntityList.map(entity -> {
                ProductResponseDto dto = entityDtoConverter.convertEntityToDto(entity);
                return dto;
            });

            return productResponsePageDto;
        }
        return Page.empty();
    }


    @Transactional(readOnly = true)
    public ProductResponseDto findProductBySku(String sku) {
        Optional<ProductEntity> productEntity = Optional.ofNullable(productRepository.findProductBySku(sku));
        if (!productEntity.isPresent()) {
            throw new ProductNotFoundException(ExceptionMessageEnum.PRODUCT_NOT_FOUND.getValue());
        }
        return entityDtoConverter.convertEntityToDto(productEntity.get());
    }

    @Transactional
    public ProductResponseDto updateProductBySku(String sku, ProductRequestDto productRequestDto) {
        ProductEntity productEntity = findProductById(sku);
        productEntity.setName(productRequestDto.getName());
        productEntity.setPrice(productRequestDto.getPrice());
        productEntity.setImageUrl(productRequestDto.getUrlImage());
        productEntity.setSize(productRequestDto.getSize());
        productEntity.setBrand(productRequestDto.getBrand());
        ProductEntity product = productRepository.saveAndFlush(productEntity);
        return entityDtoConverter.convertEntityToDto(product);
    }

    @Transactional
    public ProductResponseDto updateProductPartialBySku(String sku, Map<String, Object> changes) {

        ProductEntity productEntity = findProductById(sku);
        ProductRequestDto productRequestModel = mapProductEntityToProductRequestModel(productEntity);

        changes.forEach(
                (change, value) -> {
                    switch (change) {
                        case "name":
                            productRequestModel.setName((String) value);
                            break;
                        case "brand":
                            productRequestModel.setBrand((String) value);
                            break;
                        case "size":
                            productRequestModel.setSize((String) value);
                            break;
                        case "price":
                            productRequestModel.setPrice((BigDecimal) value);
                            break;
                        case "imageUrl":
                            productRequestModel.setUrlImage((String) value);
                            break;
                    }
                }
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(productRequestModel);
        Set<ConstraintViolation<ProductRequestDto>> violations = validator.validate(productRequestModel);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        ProductEntity productUpdate = productRepository.saveAndFlush(mapProductEntityToProductRequestModel(productRequestModel));
        return entityDtoConverter.convertEntityToDto(productUpdate);

    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productRequestDto.getSku());
        if (productEntityOptional.isPresent()) {
            throw new BadRequestException(Message.SKU_ALREADY_EXIST);
        }

        ProductEntity productEntity = ProductEntity.builder()
                .sku(productRequestDto.getSku())
                .name(productRequestDto.getName())
                .size(productRequestDto.getSize())
                .brand(productRequestDto.getBrand())
                .price(productRequestDto.getPrice())
                .imageUrl(productRequestDto.getUrlImage())
                .build();

        ProductEntity newProductEntity = productRepository.save(productEntity);
        if (productRequestDto.getOtherImages() != null && !productRequestDto.getOtherImages().isEmpty()) {
            Set<ImageProductEntity> otherImageList = productRequestDto.getOtherImages().stream()
                    .map(img -> ImageProductEntity.builder()
                            .productEntity(newProductEntity)
                            .urlImage(img.getUrlImage()).build())
                    .collect(Collectors.toSet());
            imageProductRepository.saveAll(otherImageList);


        }

        return entityDtoConverter.convertEntityToDto(newProductEntity);
    }

    @Transactional()
    public boolean deleteProduct(String sku) {
        ProductEntity productEntity = findProductById(sku);
        productRepository.delete(productEntity);
        return true;
    }


    private ProductEntity findProductById(String sku) {
        return productRepository.findById(sku)
                .orElseThrow(() -> new ProductNotFoundException(ExceptionMessageEnum.SKU_NOT_FOUND.getValue()));
    }

    private ProductRequestDto mapProductEntityToProductRequestModel(ProductEntity productEntity) {
        return ProductRequestDto.builder()
                .sku(productEntity.getSku())
                .brand(productEntity.getBrand())
                .name(productEntity.getName())
                .size(productEntity.getSize())
                .price(productEntity.getPrice())
                .urlImage(productEntity.getImageUrl())
                .build();
    }

    private ProductEntity mapProductEntityToProductRequestModel(ProductRequestDto productEntity) {
        return ProductEntity.builder()
                .sku(productEntity.getSku())
                .brand(productEntity.getBrand())
                .name(productEntity.getName())
                .size(productEntity.getSize())
                .price(productEntity.getPrice())
                .imageUrl(productEntity.getUrlImage())
                .build();
    }

}
