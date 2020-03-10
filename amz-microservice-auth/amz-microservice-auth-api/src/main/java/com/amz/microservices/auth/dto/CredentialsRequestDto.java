package com.amz.microservices.auth.dto;

import com.amz.microservices.common.object.enums.AccountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CredentialsRequestDto")
public class CredentialsRequestDto {

  @NotNull(message = "User id is required")
  @ApiModelProperty(value = "The user id", required = true)
  private UUID userId;

  @NotNull(message = "User type is required")
  @ApiModelProperty(value = "The user type", required = true)
  private AccountType accountType;

  @NotBlank(message = "The username is required")
  @ApiModelProperty(value = "The username is required", required = true)
  private String username;

  @NotBlank(message = "The password is required")
  @ApiModelProperty(value = "The password", required = true)
  private String password;

}
