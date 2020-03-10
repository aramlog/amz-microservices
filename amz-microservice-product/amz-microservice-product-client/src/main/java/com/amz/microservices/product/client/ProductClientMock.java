package com.amz.microservices.product.client;

import com.amz.microservices.common.object.dto.SuccessResponseDto;
import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class ProductClientMock implements ProductClient {

  @Override
  public ResponseEntity<ProductDto> getProduct(UUID productId) {
    return null;
  }

  @Override
  public ResponseEntity<Page<ProductDto>> getProducts(ProductFilter filter, Pageable page) {
    return null;
  }

  @Override
  public ResponseEntity<SuccessResponseDto<ProductDto>> createProduct(ProductCreateDto request) {
    return null;
  }

}
