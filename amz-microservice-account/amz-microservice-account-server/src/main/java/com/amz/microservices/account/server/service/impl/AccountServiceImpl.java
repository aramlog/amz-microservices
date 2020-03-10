package com.amz.microservices.account.server.service.impl;

import static com.amz.microservices.account.server.error.AccountErrorCode.ACCOUNT_NOT_FOUND;

import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.account.server.entity.Account;
import com.amz.microservices.account.server.entity.Address;
import com.amz.microservices.account.server.error.AccountErrorCode;
import com.amz.microservices.account.server.exception.AccountException;
import com.amz.microservices.account.server.repository.AccountRepository;
import com.amz.microservices.account.server.repository.AddressRepository;
import com.amz.microservices.account.server.repository.ProfileRepository;
import com.amz.microservices.account.server.repository.specs.AccountSpecification;
import com.amz.microservices.account.server.service.AccountService;
import com.amz.microservices.auth.client.AuthClient;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.common.object.enums.AccountType;
import com.amz.microservices.common.security.SecurityContextService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final ProfileRepository profileRepository;
  private final AddressRepository addressRepository;

  private final SecurityContextService securityContextService;
  private final MapperFacade mapper;

  private final AuthClient authClient;

  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  public AccountDto getCurrent() {
    final Account account = retrieveCurrentAccount();

    return mapper.map(account, AccountDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
  public AccountDto getById(final UUID accountId) {
    final Account account = accountRepository.findByIdAndDeleted(accountId, false)
        .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

    return mapper.map(account, AccountDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_SELLER')")
  public Page<AccountDto> getAll(final AccountFilter filter, final Pageable pageable) {
    // Create account filter specification
    final Specification<Account> spec = new AccountSpecification(filter);

    // Find all accounts by specification
    final Page<Account> pagedAccounts = accountRepository.findAll(spec, pageable);

    // Map result to response dto list
    final List<AccountDto> response = mapper.mapAsList(pagedAccounts.getContent(), AccountDto.class);

    // Create response pageable resource for dto list and return it
    return new PageImpl<>(response, pagedAccounts.getPageable(), pagedAccounts.getTotalElements());
  }

  @Override
  @Transactional
  public AccountDto create(final AccountCreateDto accountCreateDto) {
    // Check availabilities
    checkAccountAvailabilities(accountCreateDto);

    // map incoming request dto to entity
    Account account = mapper.map(accountCreateDto, Account.class);

    // persist account address is available in request
    if (accountCreateDto.getAddress() != null) {
      final Address address = mapper.map(accountCreateDto.getAddress(), Address.class);
      address.setAccount(account);
      List<Address> addr = new ArrayList<>();
      addr.add(address);
      account.setAddresses(addr);
    }

    // persist account entity
    account = accountRepository.save(account);

    // Create auth credentials
    createAuthCredentials(account.getId(), accountCreateDto);

    // map saved entity to account dto
    return mapper.map(account, AccountDto.class);
  }

  @Override
  @Transactional
  @PreAuthorize("isAuthenticated()")
  public AccountDto update(final AccountUpdateDto accountUpdateDto, final boolean partially) {
    Account account = retrieveCurrentAccount();

    // apply partially update
    if (partially) {
      if (StringUtils.isNotEmpty(accountUpdateDto.getEmail())) {
        account.getProfile().setEmail(accountUpdateDto.getEmail());
      }
      if (StringUtils.isNotEmpty(accountUpdateDto.getFirstName())) {
        account.getProfile().setFirstName(accountUpdateDto.getFirstName());
      }
      if (StringUtils.isNotEmpty(accountUpdateDto.getLastName())) {
        account.getProfile().setLastName(accountUpdateDto.getLastName());
      }
      if (accountUpdateDto.getGender() != null) {
        account.getProfile().setGender(accountUpdateDto.getGender());
      }
    } else {
      String username = accountUpdateDto.getUsername();
      account = mapper.map(accountUpdateDto, Account.class);
      account.setUsername(username);
    }

    return mapper.map(accountRepository.save(account), AccountDto.class);
  }

  @Override
  @Transactional
  @PreAuthorize("isAuthenticated()")
  public void delete() {
    Account account = retrieveCurrentAccount();

    if (account.isDeleted()) {
      throw new AccountException(AccountErrorCode.ACCOUNT_ALREADY_DELETED);
    }

    account.setDeleted(true);

    accountRepository.save(account);
  }

  /**
   * Retrieve current requester account
   *
   * @return {@link Account} of the current requester
   */
  private Account retrieveCurrentAccount() {
    final UUID currentUserId = securityContextService.getCurrentUserId();
    return accountRepository.findByIdAndDeleted(currentUserId, false)
        .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));
  }

  /**
   * Create auth credentials based on account details.
   *
   * @param userId The account id
   * @param request {@link AccountCreateDto} which contains the account details
   */
  private void createAuthCredentials(final UUID userId, final AccountCreateDto request) {
    final CredentialsRequestDto credentialsRequest = CredentialsRequestDto.builder()
        .userId(userId)
        .accountType(request.getType())
        .username(request.getUsername())
        .password(request.getPassword())
        .build();

    authClient.createCredentials(credentialsRequest);
  }

  /**
   * Check account availabilities
   *
   * @param request {@link AccountCreateDto} which contains account details
   */
  private void checkAccountAvailabilities(final AccountCreateDto request) {
    if (accountRepository.existsByUsername(request.getUsername())) {
      log.warn("Account already exists with username: {}", request.getUsername());

      throw new AccountException(AccountErrorCode.ACCOUNT_USERNAME_ALREADY_EXISTS);
    }

    if (profileRepository.existsByEmail(request.getEmail())) {
      log.warn("Account already exists with email: {}", request.getEmail());

      throw new AccountException(AccountErrorCode.ACCOUNT_EMAIL_ALREADY_EXISTS);
    }

    if (profileRepository.existsByMobile(request.getMobile())) {
      log.warn("Account already exists with mobile: {}", request.getEmail());

      throw new AccountException(AccountErrorCode.ACCOUNT_MOBILE_ALREADY_EXISTS);
    }
  }

}
