package com.amz.microservices.product.server.service;

import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Product service.
 *
 * @author Aram Kirakosyan
 */
public interface ProductService {

  /**
   * Create product
   *
   * @param productCreateDto The {@link ProductCreateDto} which contains details
   * @return created {@link ProductDto}
   */
  ProductDto create(ProductCreateDto productCreateDto);

  /**
   * Get product by id
   *
   * @param id The product id
   * @return {@link ProductDto}
   */
  ProductDto getById(UUID id);

  /**
   * Get products by the given filter and pageable details
   *
   * @param filter The filter which contains search details
   * @param pageable The pageable details
   * @return Page response of {@link ProductDto}
   */
  Page<ProductDto> getAll(ProductFilter filter, Pageable pageable);

}
