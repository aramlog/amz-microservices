package com.amz.microservices.account.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.amz.microservices.account.dto.AddressDto;
import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.account.server.entity.Account;
import com.amz.microservices.account.server.entity.Address;
import com.amz.microservices.account.server.entity.Profile;
import com.amz.microservices.account.server.error.AccountErrorCode;
import com.amz.microservices.account.server.exception.AccountException;
import com.amz.microservices.account.server.repository.AccountRepository;
import com.amz.microservices.account.server.repository.AddressRepository;
import com.amz.microservices.account.server.repository.ProfileRepository;
import com.amz.microservices.auth.client.AuthClient;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.common.object.enums.AccountType;
import com.amz.microservices.common.object.enums.GenderType;
import com.amz.microservices.common.security.SecurityContextService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.CastUtils;

public class AccountServiceUnitTest {

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private AddressRepository addressRepository;
  @Mock
  private ProfileRepository profileRepository;
  @Mock
  private SecurityContextService securityContextService;
  @Spy
  private MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();
  @Mock
  private AuthClient authClient;

  @Spy
  @InjectMocks
  private AccountServiceImpl accountService;

  private static final String USERNAME = "John Doe";
  private static final String COUNTRY = "Hartford";
  private static final String EMAIL = "john.doe@gmail.com";

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldGetByIdSuccess() {
    // GIVEN
    final Account account = new Account();
    account.setId(UUID.randomUUID());
    account.setType(AccountType.CUSTOMER);

    when(accountRepository.findByIdAndDeleted(account.getId(), false)).thenReturn(Optional.of(account));

    // WHEN
    final AccountDto response = accountService.getById(account.getId());

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getId()).isNotNull().isEqualTo(account.getId().toString());
    assertThat(response.getType()).isNotNull().isEqualTo(account.getType());

    verify(accountRepository).findByIdAndDeleted(account.getId(), false);
    verify(mapper).map(account, AccountDto.class);
  }

  @Test
  public void shouldNotGetByIdNotFound() {
    // GIVEN
    final UUID accountId = UUID.randomUUID();

    when(accountRepository.findByIdAndDeleted(accountId, false)).thenReturn(Optional.empty());

    // WHEN
    Throwable t = catchThrowable(() -> accountService.getById(accountId));

    // THEN
    assertThat(t).isNotNull();
    assertThat(t instanceof AccountException).isTrue();

    AccountException accountException = CastUtils.cast(t);

    assertThat(accountException).isNotNull();
    assertThat(accountException.getErrorSlag()).isEqualTo(AccountErrorCode.ACCOUNT_NOT_FOUND.name());

    verify(accountRepository).findByIdAndDeleted(accountId, false);
    verifyNoInteractions(mapper);
  }

  @Test
  public void shouldGetAllSuccess() {
    // GIVEN
    final AccountFilter accountFilter = new AccountFilter();
    accountFilter.setSearchKey(USERNAME);

    final Account account = new Account();
    account.setId(UUID.randomUUID());

    final List<Account> accountList = Collections.singletonList(account);

    when(accountRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(accountList));

    // WHEN
    final Page<AccountDto> response = accountService.getAll(accountFilter, PageRequest.of(0, 10));

    // THEN
    assertThat(response).isNotEmpty();
    assertThat(response.getSize()).isEqualTo(1);

    final AccountDto accountResponseDto = response.getContent().get(0);

    assertThat(accountResponseDto.getId()).isNotNull().isEqualTo(account.getId().toString());

    verify(accountRepository).findAll(any(Specification.class), any(Pageable.class));
    verify(mapper).mapAsList(accountList, AccountDto.class);
  }

  @Test
  public void shouldCreateAccountWithAddressSuccess() {
    // GIVEN
    final AccountCreateDto accountRequest = new AccountCreateDto();
    accountRequest.setType(AccountType.CUSTOMER);
    accountRequest.setUsername(USERNAME);

    final AddressDto addressRequest = new AddressDto();
    addressRequest.setCity(COUNTRY);

    accountRequest.setAddress(addressRequest);

    when(accountRepository.save(any())).then(input -> {
      final Account account = input.getArgument(0);
      account.setId(UUID.randomUUID());

      return account;
    });

    ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

    // WHEN
    accountService.create(accountRequest);

    // THEN
    then(accountRepository).should().save(argumentCaptor.capture());

    final Account savedAccount = argumentCaptor.getValue();

    assertThat(savedAccount).isNotNull();
    assertThat(savedAccount.getType()).isNotNull().isEqualTo(AccountType.CUSTOMER);
    assertThat(savedAccount.getAddresses()).isNotNull().isNotEmpty();

    final Address address = savedAccount.getAddresses().get(0);

    assertThat(address.getCity()).isNotNull().isEqualTo(COUNTRY);

    verify(authClient).createCredentials(any(CredentialsRequestDto.class));
  }

  @Test
  public void shouldCreateAccountWithoutAddressSuccess() {
    // GIVEN
    final AccountCreateDto createRequest = new AccountCreateDto();
    createRequest.setType(AccountType.CUSTOMER);
    createRequest.setUsername(USERNAME);

    when(accountRepository.save(any())).then(input -> {
      final Account account = input.getArgument(0);
      account.setId(UUID.randomUUID());

      return account;
    });

    final ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

    // WHEN
    accountService.create(createRequest);

    // THEN
    then(accountRepository).should().save(argumentCaptor.capture());

    final Account savedAccount = argumentCaptor.getValue();

    assertThat(savedAccount).isNotNull();
    assertThat(savedAccount.getType()).isNotNull().isEqualTo(AccountType.CUSTOMER);
    assertThat(savedAccount.getAddresses()).isNull();

    verify(authClient).createCredentials(any(CredentialsRequestDto.class));
  }

  @Test
  public void shouldUpdateAccountPartiallySuccess() {
    // GIVEN
    final UUID currentUserId = UUID.randomUUID();

    final Account account = new Account();

    final Profile profile = new Profile();
    profile.setGender(GenderType.FEMALE);
    profile.setEmail(EMAIL);

    account.setProfile(profile);

    final AccountUpdateDto updateRequest = new AccountUpdateDto();
    updateRequest.setGender(GenderType.MALE);

    when(securityContextService.getCurrentUserId()).thenReturn(currentUserId);
    when(accountRepository.findByIdAndDeleted(currentUserId, false)).thenReturn(Optional.of(account));

    final ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

    // WHEN
    accountService.update(updateRequest, true);

    // THEN
    then(accountRepository).should().save(argumentCaptor.capture());

    final Account updatedAccount = argumentCaptor.getValue();

    assertThat(updatedAccount).isNotNull();
    assertThat(updatedAccount.getProfile().getGender()).isNotNull().isEqualTo(updateRequest.getGender());
    assertThat(updatedAccount.getProfile().getEmail()).isNotNull().isNotEqualTo(updateRequest.getEmail());
  }

  @Test
  public void shouldDeleteAccountSuccess() {
    // GIVEN
    final UUID currentUserId = UUID.randomUUID();

    final Account account = new Account();
    account.setDeleted(false);

    when(securityContextService.getCurrentUserId()).thenReturn(currentUserId);
    when(accountRepository.findByIdAndDeleted(currentUserId, false)).thenReturn(Optional.of(account));

    final ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

    // WHEN
    accountService.delete();

    // THEN
    then(accountRepository).should().save(argumentCaptor.capture());

    final Account deletedAccount = argumentCaptor.getValue();

    assertThat(deletedAccount).isNotNull();
    assertThat(deletedAccount.isDeleted()).isTrue();
  }

  @Test
  public void shouldNotDeleteAccountNotFound() {
    // GIVEN
    final UUID currentUserId = UUID.randomUUID();

    when(securityContextService.getCurrentUserId()).thenReturn(currentUserId);
    when(accountRepository.findByIdAndDeleted(currentUserId, false)).thenReturn(Optional.empty());

    // WHEN
    Throwable t = catchThrowable(() -> accountService.delete());

    // THEN
    assertThat(t).isNotNull();
    assertThat(t instanceof AccountException).isTrue();

    AccountException accountException = CastUtils.cast(t);

    assertThat(accountException).isNotNull();
    assertThat(accountException.getErrorSlag()).isEqualTo(AccountErrorCode.ACCOUNT_NOT_FOUND.name());

    verify(accountRepository).findByIdAndDeleted(currentUserId, false);
  }

}
