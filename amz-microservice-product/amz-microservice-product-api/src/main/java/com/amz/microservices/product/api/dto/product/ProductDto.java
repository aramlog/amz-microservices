package com.amz.microservices.product.api.dto.product;

import com.amz.microservices.common.object.Pattern;
import com.amz.microservices.product.api.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder
@ApiModel(value = "ProductDto")
public class ProductDto extends ProductCreateDto {

  @ApiModelProperty(value = "Product unique identifier")
  private String id;

  @ApiModelProperty(value = "Product category")
  private CategoryDto categoryDto;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE_TIME_PATTERN)
  @ApiModelProperty(value = "Updated date")
  private LocalDateTime updatedDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE_TIME_PATTERN)
  @ApiModelProperty(value = "Created date")
  private LocalDateTime createdDate;

}
