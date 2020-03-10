package com.amz.microservices.product.server.repository.specs;

import com.amz.microservices.common.object.utils.SpecUtils;
import com.amz.microservices.product.api.filter.ProductFilter;
import com.amz.microservices.product.server.entity.Product;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {

  private final ProductFilter filter;

  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  private static final String QUANTITY = "quantity";
  private static final String PRICE = "price";

  @Override
  public Predicate toPredicate(@NonNull Root<Product> root, @NonNull CriteriaQuery<?> cq, @NonNull CriteriaBuilder cb) {
    Predicate and = cb.conjunction();

    if (filter != null) {
      if (filter.getSearchKey() != null) {
        final String searchPattern = SpecUtils.searchTermLikePattern(filter.getSearchKey());

        // Build search predicate from root
        final Predicate likeSearch = SpecUtils.buildSearchLikePredicateFromRoot(root, cb, searchPattern,
            NAME, DESCRIPTION);

        and.getExpressions().add(likeSearch);
      }

      if (filter.getPriceFrom() != null) {
        and.getExpressions().add(cb.greaterThanOrEqualTo(root.get(PRICE), filter.getPriceFrom()));
      }
      if (filter.getPriceTo() != null) {
        and.getExpressions().add(cb.lessThanOrEqualTo(root.get(PRICE), filter.getPriceTo()));
      }
      if (filter.getQuantityFrom() != null) {
        and.getExpressions().add(cb.greaterThanOrEqualTo(root.get(QUANTITY), filter.getQuantityFrom()));
      }
      if (filter.getQuantityTo() != null) {
        and.getExpressions().add(cb.lessThanOrEqualTo(root.get(QUANTITY), filter.getQuantityTo()));
      }

      // Add search by dates filter
      SpecUtils.addCreatedDateFilter(filter.getDateFrom(), filter.getDateTo(), and, root, cb);

    }
    return and;
  }

}
