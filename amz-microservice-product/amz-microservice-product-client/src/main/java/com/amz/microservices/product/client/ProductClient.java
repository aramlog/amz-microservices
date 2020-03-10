package com.amz.microservices.product.client;

import com.amz.microservices.common.contract.feign.FeignClientConfig;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import com.amz.microservices.product.api.api.ProductApi;
import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "amz-microservice-product", configuration = {FeignClientConfig.class})
public interface ProductClient extends ProductApi {

  @Override
  @GetMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ProductDto> getProduct(@PathVariable(value = "productId") UUID productId);

  @Override
  @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<ProductDto>> getProducts(ProductFilter filter,
      @PageableDefault @SortDefault(sort = "updatedDate", direction = Sort.Direction.DESC) Pageable page);

  @Override
  @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SuccessResponseDto<ProductDto>> createProduct(@RequestBody @Valid ProductCreateDto request);

}
