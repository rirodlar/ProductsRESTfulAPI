package com.falabella.test.products.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "IMAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProductEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IMAGE_URL", nullable = false, length = 50)
    private String urlImage;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "sku", foreignKey = @ForeignKey(name = "FK_IMAGE_PRODUCT_PRODUCT"))
    private ProductEntity productEntity;

}
