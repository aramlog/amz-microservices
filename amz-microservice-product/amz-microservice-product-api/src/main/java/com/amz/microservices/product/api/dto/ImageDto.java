package com.amz.microservices.product.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("ImageDto")
public class ImageDto {

  @ApiModelProperty(value = "Image unique identifier")
  private Long id;

  @NotBlank(message = "Category name is required")
  @ApiModelProperty(value = "Category name", example = "Clothes", required = true)
  private String imageUrl;
}
