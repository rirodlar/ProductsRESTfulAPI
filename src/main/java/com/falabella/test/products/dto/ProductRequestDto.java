package com.falabella.test.products.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

   @NotNull
   @NotBlank
   @Pattern(regexp= "(FAL)-\\d{7}")
   private String sku;

   @NotBlank
   @Size(min = 3, max = 50)
   private String name;

   @NotBlank
   @Size(min = 3, max = 50)
   private String brand;


   @Size(min=1)
   private String size;

   @DecimalMin("1.0")
   @DecimalMax("99999999.00")
   private BigDecimal price;

   @NotBlank
   @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
   private String urlImage;

   @Valid
   private Set<ImageDto> otherImages = new HashSet<>();



}
