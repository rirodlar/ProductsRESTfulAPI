package com.falabella.test.products.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "class representing an product to processed")
public class ProductRequestDto {

    @NotNull
    @NotBlank
    @Pattern(regexp = "(FAL)-\\d{7}")
    @ApiModelProperty(notes = "sku", example = "FA-2000045", required = true, position = 0)
    private String sku;

    @NotBlank
    @Size(min = 3, max = 50)
    @ApiModelProperty(notes = "sku", example = "FA-2000045", required = true, position = 1)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @ApiModelProperty(notes = "Name of the brand", example = "Sybilla", required = true, position = 2)
    private String brand;


    @Size(min = 1)
    @ApiModelProperty(notes = "Size of the product", example = "XL", position = 3)
    private String size;

    @DecimalMin("1.0")
    @DecimalMax("99999999.00")
    @ApiModelProperty(notes = "Sell price", example = "1.00", required = true, position = 4)
    private BigDecimal price;

    @NotBlank
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    @ApiModelProperty(notes = "URL of the principal image of the\n" +
            "product", example = "http://www.falalla/image.jpg", required = true, position = 5)
    private String urlImage;

    @Valid
    private Set<ImageDto> otherImages;


}
