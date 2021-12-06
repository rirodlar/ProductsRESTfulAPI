package com.falabella.test.products.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@Table(name = "IMAGE")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ImageProductEntity extends CommonEntity {



    @Id
    @Column(name = "IMAGE_URL", nullable = false, length = 50)
    private String urlImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "sku", foreignKey = @ForeignKey(name = "FK_IMAGE_PRODUCT_PRODUCT"))
    private ProductEntity productEntity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImageProductEntity that = (ImageProductEntity) o;
        return Objects.equals(urlImage, that.urlImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), urlImage);
    }
}
