package com.amz.microservices.product.server.controller;

import com.amz.microservices.common.object.dto.SuccessResponseDto;
import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import com.amz.microservices.product.client.ProductClient;
import com.amz.microservices.product.server.service.ProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductClient {

  private final ProductService productService;

  @Override
  public ResponseEntity<ProductDto> getProduct(UUID productId) {
    log.info("Trying to get product by id: {}", productId);

    final ProductDto response = productService.getById(productId);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Page<ProductDto>> getProducts(ProductFilter filter, Pageable page) {
    log.info("Trying to get products by the given filter: {} and pageable details: {}", filter, page);

    final Page<ProductDto> response = productService.getAll(filter, page);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<SuccessResponseDto<ProductDto>> createProduct(ProductCreateDto request) {
    log.info("Trying to create product by the given details: {}", request);

    final ProductDto response = productService.create(request);

    return ResponseEntity.ok(new SuccessResponseDto<>("Product created successfully", response));
  }

}
