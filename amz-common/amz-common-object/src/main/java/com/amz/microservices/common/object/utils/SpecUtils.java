package com.amz.microservices.common.object.utils;

import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecUtils {

  private static final String CREATED_DATE = "createdDate";

  public static Predicate buildSearchLikePredicateFromRoot(final Root<?> root, final CriteriaBuilder cb,
      final String searchPattern, final String... params) {

    final Predicate likeSearch = cb.disjunction();

    for (String param : params) {
      likeSearch.getExpressions().add(cb.like(cb.lower(root.get(param)), searchPattern));
    }

    return likeSearch;
  }

  public static void addSearchFromJoinedTable(final Predicate predicate, final Join<?, ?> join,
      final CriteriaBuilder cb, final String searchPattern, final String... params) {

    for (String param : params) {
      predicate.getExpressions().add(cb.like(cb.lower(join.get(param)), searchPattern));
    }
  }

  public static void addCreatedDateFilter(final LocalDate dateFrom, final LocalDate dateTo, final Predicate predicate,
      final Root<?> root, final CriteriaBuilder cb) {

    if (dateFrom != null) {
      predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(CREATED_DATE), dateFrom.atStartOfDay()));
    }
    if (dateTo != null) {
      predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(CREATED_DATE), dateTo.plusDays(1).atStartOfDay()));
    }
  }

  /**
   * Build search like pattern based on provided search key
   *
   * @param searchKey based on which like pattern string must be generated
   * @return generated like pattern string
   */
  public String searchTermLikePattern(final String searchKey) {
    return "%" + searchKey.toLowerCase() + "%";
  }

}
