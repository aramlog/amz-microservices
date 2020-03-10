package com.amz.microservices.account.server.service;

import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.dto.account.AccountDto;
import com.amz.microservices.account.dto.account.AccountUpdateDto;
import com.amz.microservices.account.filter.AccountFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Account service.
 *
 * @author Aram Kirakosyan
 */
public interface AccountService {

  /**
   * Retrieve current logged in user account.
   *
   * @return {@link AccountDto} found account dto
   */
  AccountDto getCurrent();

  /**
   * Find account by id.
   *
   * @param accountId {@link UUID} account unique identifier
   * @return {@link AccountDto} found account dto
   */
  AccountDto getById(UUID accountId);

  /**
   * Find accounts by specified criteria. Supported pagination and sorting.
   *
   * @param filter {@link AccountFilter} filtering criteria
   * @param pageable {@link Pageable} pagination support
   * @return {@link Page<AccountDto>} pageable account dtos
   */
  Page<AccountDto> getAll(AccountFilter filter, final Pageable pageable);

  /**
   * Create a new account
   *
   * @param accountCreateDto {@link AccountCreateDto} dto for creating account
   * @return {@link AccountDto} saved account dto
   */
  AccountDto create(AccountCreateDto accountCreateDto);

  /**
   * Update account (full or partially)
   *
   * @param accountUpdateDto {@link AccountUpdateDto} dto for updating account
   * @param partially if true apply partial updating.
   * @return {@link AccountDto} update account dto
   */
  AccountDto update(AccountUpdateDto accountUpdateDto, boolean partially);

  /**
   * Delete account (mark as deleted)
   */
  void delete();
}
