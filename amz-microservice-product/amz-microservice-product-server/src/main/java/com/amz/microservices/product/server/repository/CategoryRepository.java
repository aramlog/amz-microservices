package com.amz.microservices.product.server.repository;

import com.amz.microservices.product.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  boolean existsById(@NonNull Long id);

}
