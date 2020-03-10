package com.amz.microservices.account.client;

import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class AccountClientMock implements AccountClient {

  @Override
  public ResponseEntity<AccountDto> getAccount(UUID accountId) {
    return null;
  }

  @Override
  public ResponseEntity<Page<AccountDto>> getAccounts(AccountFilter filter, Pageable page) {
    return null;
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> createAccount(@Valid AccountCreateDto accountCreateDto) {
    return null;
  }

  @Override
  public ResponseEntity<AccountDto> getCurrentAccount() {
    return null;
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> updateAccount(@Valid AccountUpdateDto accountUpdateDto) {
    return null;
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> updateAccountPartially(
      @Valid AccountUpdateDto accountUpdateDto) {
    return null;
  }

  @Override
  public ResponseEntity<SuccessResponseDto<Void>> deleteAccount() {
    return null;
  }

}
