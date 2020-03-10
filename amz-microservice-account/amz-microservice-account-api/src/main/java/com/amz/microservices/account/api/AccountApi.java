package com.amz.microservices.account.api;

import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.common.object.dto.ErrorResponseDto;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api(tags = "Account")
public interface AccountApi {

  @ApiOperation(value = "Get current account")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = AccountDto.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 404, message = "Account not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 403, message = "Account blocked", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<AccountDto> getCurrentAccount();

  @ApiOperation(value = "Get account by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = AccountDto.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 404, message = "Account not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 403, message = "Account blocked", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<AccountDto> getAccount(@ApiParam(name = "accountId", required = true) UUID accountId);

  @ApiOperation(value = "Get accounts by criteria")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = Page.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<Page<AccountDto>> getAccounts(@ApiParam(value = "Accounts filer") AccountFilter filter,
      final Pageable page);

  @ApiOperation(value = "Create account")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successful"),
      @ApiResponse(code = 412, message = "Validation failed", response = ErrorResponseDto.class),
      @ApiResponse(code = 412, message = "Account already exist", response = ErrorResponseDto.class)
  })
  ResponseEntity<SuccessResponseDto<AccountDto>> createAccount(
      @ApiParam(name = "Account payload", required = true) AccountCreateDto accountCreateDto);

  @ApiOperation(value = "Update account")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = AccountDto.class),
      @ApiResponse(code = 404, message = "Account not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 412, message = "Validation failed", response = ErrorResponseDto.class),
  })
  ResponseEntity<SuccessResponseDto<AccountDto>> updateAccount(
      @ApiParam(name = "Account payload", required = true) AccountUpdateDto accountUpdateDto);

  @ApiOperation(value = "Update account partially")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = AccountDto.class),
      @ApiResponse(code = 404, message = "Account not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 412, message = "Validation failed", response = ErrorResponseDto.class),
  })
  ResponseEntity<SuccessResponseDto<AccountDto>> updateAccountPartially(
      @ApiParam(name = "Account payload", required = true) AccountUpdateDto accountUpdateDto);


  @ApiOperation(value = "Delete account")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful"),
      @ApiResponse(code = 404, message = "Account not found", response = ErrorResponseDto.class)
  })
  ResponseEntity<SuccessResponseDto<Void>> deleteAccount();

}
