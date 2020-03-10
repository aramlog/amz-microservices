package com.amz.microservices.product.api.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "ProductCreateDto")
public class ProductCreateDto {

  @NotNull(message = "Category id is required")
  @ApiModelProperty(value = "Category id", example = "1", required = true)
  private Long categoryId;

  @NotBlank(message = "Product name is required")
  @ApiModelProperty(value = "Product name", example = "jacket", required = true)
  private String name;

  @NotBlank(message = "Product description is required")
  @ApiModelProperty(value = "Product description", example = "Lorem Ipsum", required = true)
  private String description;

  @NotNull(message = "Product price is required")
  @ApiModelProperty(value = "Product price", example = "99", required = true)
  private Integer price;

  @NotNull(message = "Product quantity is required")
  @ApiModelProperty(value = "Product quantity ", example = "3")
  private Integer quantity;
}


