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

@Data
public class ProductRequest {

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
   @Pattern(regexp = "(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?")
   private String urlImage;

   @Valid
   private Set<ImageDto> otherImageSet = new HashSet<>();

   @Data
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   public static class ImageDto{
      @NotBlank
      @Pattern(regexp = "(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?")
      private String urlImage;
   }

}