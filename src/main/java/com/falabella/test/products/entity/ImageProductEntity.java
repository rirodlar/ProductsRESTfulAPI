package com.falabella.test.products.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    private ProductEntity productEntity;

}
