package com.amz.microservices.account.server.controller;

import com.amz.microservices.account.client.AccountClient;
import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.account.server.service.AccountService;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import com.amz.microservices.common.security.SecurityContextService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountClient {

  private final AccountService accountService;
  private final SecurityContextService securityContextService;

  @Override
  public ResponseEntity<AccountDto> getAccount(final UUID accountId) {
    log.info("Trying to get account details with id : {} ", accountId);

    return new ResponseEntity<>(accountService.getById(accountId), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<AccountDto> getCurrentAccount() {
    log.info("Trying to get currently logged in account details with id : {} ",
        securityContextService.getCurrentUserId());

    return new ResponseEntity<>(accountService.getCurrent(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Page<AccountDto>> getAccounts(final AccountFilter filter, final Pageable pageable) {
    log.info("Trying to find accounts with filter : {} ", filter);

    return new ResponseEntity<>(accountService.getAll(filter, pageable), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> createAccount(final AccountCreateDto accountCreateDto) {
    log.info("Trying to create a new account with details : {} ", accountCreateDto);

    return new ResponseEntity<>(new SuccessResponseDto<>("Account successfully created",
        accountService.create(accountCreateDto)), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> updateAccount(final AccountUpdateDto accountUpdateDto) {
    log.info("Trying to update account with id : {} and details : {} ",
        securityContextService.getCurrentUserId(), accountUpdateDto);

    return new ResponseEntity<>(new SuccessResponseDto<>("Account successfully updated",
        accountService.update(accountUpdateDto, false)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<SuccessResponseDto<AccountDto>> updateAccountPartially(
      final AccountUpdateDto accountUpdateDto) {

    log.info("Trying to partially update account with id : {} and details : {} ",
        securityContextService.getCurrentUserId(), accountUpdateDto);

    return new ResponseEntity<>(new SuccessResponseDto<>("Account successfully updated",
        accountService.update(accountUpdateDto, true)), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<SuccessResponseDto<Void>> deleteAccount() {
    log.info("Trying to delete account with id : {} ", securityContextService.getCurrentUserId());

    accountService.delete();

    return new ResponseEntity<>(new SuccessResponseDto<>("Account successfully deleted", null),
        HttpStatus.OK);
  }
}
