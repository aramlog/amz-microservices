package com.amz.microservices.account.server.repository.specs;

import com.amz.microservices.account.filter.AccountFilter;
import com.amz.microservices.account.server.entity.Account;
import com.amz.microservices.common.object.utils.SpecUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class AccountSpecification implements Specification<Account> {

  private static final String FIELD_PROFILE = "profile";
  private static final String FIELD_USERNAME = "username";
  private static final String FIELD_FIRST_NAME = "firstName";
  private static final String FIELD_LAST_NAME = "lastName";
  private static final String FIELD_EMAIL = "email";
  private static final String FIELD_MOBILE = "mobile";
  private static final String FIELD_TYPE = "type";

  private final AccountFilter filter;

  @Override
  public Predicate toPredicate(@NonNull Root<Account> root, @NonNull CriteriaQuery<?> cq, @NonNull CriteriaBuilder cb) {
    Predicate and = cb.conjunction();

    if (filter != null) {
      if (filter.getSearchKey() != null) {
        Join<?, ?> profileJoin = root.join(FIELD_PROFILE, JoinType.INNER);

        final String searchPattern = SpecUtils.searchTermLikePattern(filter.getSearchKey());

        // Build search predicate from root
        final Predicate likeSearch = SpecUtils.buildSearchLikePredicateFromRoot(root, cb, searchPattern,
            FIELD_USERNAME);

        // Search by profile fields
        SpecUtils.addSearchFromJoinedTable(likeSearch, profileJoin, cb, searchPattern,
            FIELD_FIRST_NAME, FIELD_LAST_NAME, FIELD_EMAIL, FIELD_MOBILE);

        and.getExpressions().add(likeSearch);
      }

      // Add search by dates filter
      SpecUtils.addCreatedDateFilter(filter.getDateFrom(), filter.getDateTo(), and, root, cb);

      if (filter.getType() != null) {
        and.getExpressions().add(cb.equal(root.get(FIELD_TYPE), filter.getType()));
      }
    }
    return and;
  }
}
