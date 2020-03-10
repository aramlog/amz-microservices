package com.amz.microservices.account.dto.account;

import com.amz.microservices.account.dto.AddressDto;
import com.amz.microservices.account.dto.ProfileDto;
import com.amz.microservices.common.object.Pattern;
import com.amz.microservices.common.object.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder
@ApiModel(value = "AccountDto")
public class AccountDto {

  @ApiModelProperty(value = "Account unique identifier")
  private String id;

  @ApiModelProperty(value = "Account type")
  private AccountType type;

  @ApiModelProperty(value = "Full name")
  private String fullName;

  @ApiModelProperty(value = "Account username")
  private String username;

  @ApiModelProperty(value = "Account profile")
  private ProfileDto profile;

  @ApiModelProperty(value = "Account address")
  private List<AddressDto> addresses;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE_TIME_PATTERN)
  @ApiModelProperty(value = "Updated date")
  private LocalDateTime updatedDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE_TIME_PATTERN)
  @ApiModelProperty(value = "Created date")
  private LocalDateTime createdDate;

  public String getFullName() {
    if (profile != null) {
      this.fullName = String.join(" ", this.profile.getFirstName(), this.profile.getLastName());
    }
    return fullName;
  }
}
