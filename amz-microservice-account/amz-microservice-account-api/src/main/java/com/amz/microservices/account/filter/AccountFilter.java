package com.amz.microservices.account.filter;

import com.amz.microservices.common.object.enums.AccountType;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilter {

  @ApiModelProperty(value = "Search key based on which search must be done")
  private String searchKey;

  @ApiModelProperty(value = "Created date from YYYY-MM-DD")
  private LocalDate dateFrom;

  @ApiModelProperty(value = "Created date to YYYY-MM-DD")
  private LocalDate dateTo;

  @ApiModelProperty(value = "Account type", allowableValues = "CUSTOMER, ADMIN, SELLER")
  private AccountType type;
}
