package com.falabella.test.products.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Builder
@Entity
@Table(name = "PRODUCT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends CommonEntity {


    @Id
    @Column(name = "SKU", unique = true,  nullable = false, length = 50)
    private String sku;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "BRAND", nullable = false, length = 50)
    private String brand;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "PRICE", nullable = false, scale = 2, precision = 10)
    private BigDecimal price;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productEntity")
    private Set<ImageProductEntity> imageProductEntitySet;

}
