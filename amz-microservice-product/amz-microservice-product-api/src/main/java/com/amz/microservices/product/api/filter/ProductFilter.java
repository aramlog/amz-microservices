package com.amz.microservices.product.api.filter;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {

  @ApiModelProperty(value = "Search key based on which search must be done")
  private String searchKey;

  @ApiModelProperty(value = "Price from")
  private Integer priceFrom;

  @ApiModelProperty(value = "Price to")
  private Integer priceTo;

  @ApiModelProperty(value = "Quantity from")
  private Integer quantityFrom;

  @ApiModelProperty(value = "QuantityTo")
  private Integer quantityTo;

  @ApiModelProperty(value = "Created date from YYYY-MM-DD")
  private LocalDate dateFrom;

  @ApiModelProperty(value = "Created date to YYYY-MM-DD")
  private LocalDate dateTo;
}
