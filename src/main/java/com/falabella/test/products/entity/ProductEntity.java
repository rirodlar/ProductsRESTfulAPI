package com.falabella.test.products.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productEntity")
    private Set<ImageProductEntity> otherImages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductEntity that = (ProductEntity) o;
        return sku.equals(that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sku);
    }
}
