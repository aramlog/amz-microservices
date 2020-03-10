package com.amz.microservices.product.server.service.impl;

import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import com.amz.microservices.product.server.entity.Product;
import com.amz.microservices.product.server.error.ProductErrorCode;
import com.amz.microservices.product.server.exception.ProductException;
import com.amz.microservices.product.server.repository.CategoryRepository;
import com.amz.microservices.product.server.repository.ProductRepository;
import com.amz.microservices.product.server.repository.specs.ProductSpecification;
import com.amz.microservices.product.server.service.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
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
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  private final MapperFacade mapper;

  @Override
  @Transactional
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SELLER')")
  public ProductDto create(final ProductCreateDto productCreateDto) {
    if (!categoryRepository.existsById(productCreateDto.getCategoryId())) {
      log.info("Category with id: {} not found", productCreateDto.getCategoryId());

      throw new ProductException(ProductErrorCode.PRODUCT_CATEGORY_NOT_FOUND);
    }

    Product product = mapper.map(productCreateDto, Product.class);
    product.setCategory(categoryRepository.getOne(productCreateDto.getCategoryId()));

    product = productRepository.save(product);

    log.info("Product created successfully with details: {}", productCreateDto);

    return mapper.map(product, ProductDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDto getById(final UUID id) {
    final Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

    return mapper.map(product, ProductDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductDto> getAll(final ProductFilter filter, final Pageable pageable) {
    // Create product filter specification
    final Specification<Product> spec = new ProductSpecification(filter);

    // Find all products by the specification
    final Page<Product> pagedProducts = productRepository.findAll(spec, pageable);

    // Map result to response dto list
    final List<ProductDto> response = mapper.mapAsList(pagedProducts.getContent(), ProductDto.class);

    // Create response pageable resource for dto list and return it
    return new PageImpl<>(response, pagedProducts.getPageable(), pagedProducts.getTotalElements());
  }

}
