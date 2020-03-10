package com.amz.microservices.account.client;

import com.amz.microservices.account.api.AccountApi;
import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.common.contract.feign.FeignClientConfig;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "amz-microservice-account", configuration = {FeignClientConfig.class})
public interface AccountClient extends AccountApi {

  @Override
  @GetMapping(value = "/accounts/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<AccountDto> getAccount(@PathVariable(value = "accountId") UUID accountId);

  @Override
  @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<AccountDto>> getAccounts(AccountFilter filter,
      @PageableDefault @SortDefault(sort = "updatedDate", direction = Sort.Direction.DESC) Pageable page);

  @Override
  @PostMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SuccessResponseDto<AccountDto>> createAccount(@RequestBody @Valid AccountCreateDto accountCreateDto);

  @Override
  @GetMapping(value = "/accounts/me", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<AccountDto> getCurrentAccount();

  @Override
  @PutMapping(value = "/accounts/me", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SuccessResponseDto<AccountDto>> updateAccount(@RequestBody @Valid AccountUpdateDto accountUpdateDto);

  @Override
  @PatchMapping(value = "/accounts/me", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SuccessResponseDto<AccountDto>> updateAccountPartially(
      @RequestBody @Valid AccountUpdateDto accountUpdateDto);

  @Override
  @DeleteMapping(value = "/accounts/me", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SuccessResponseDto<Void>> deleteAccount();
}
