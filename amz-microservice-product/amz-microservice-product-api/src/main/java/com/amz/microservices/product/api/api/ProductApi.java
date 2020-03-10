package com.amz.microservices.product.api.api;

import com.amz.microservices.common.object.dto.ErrorResponseDto;
import com.amz.microservices.common.object.dto.SuccessResponseDto;
import com.amz.microservices.product.api.dto.product.ProductCreateDto;
import com.amz.microservices.product.api.dto.product.ProductDto;
import com.amz.microservices.product.api.filter.ProductFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api(tags = "Product")
public interface ProductApi {

  @ApiOperation(value = "Get product by id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = ProductDto.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 404, message = "Product not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 403, message = "Product blocked", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<ProductDto> getProduct(@ApiParam(name = "productId", required = true) UUID productId);

  @ApiOperation(value = "Get products by criteria")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful", response = Page.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<Page<ProductDto>> getProducts(@ApiParam(value = "Products filer") ProductFilter filter, Pageable page);

  @ApiOperation(value = "Create product")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successful"),
      @ApiResponse(code = 412, message = "Validation failed", response = ErrorResponseDto.class),
      @ApiResponse(code = 412, message = "Product already exist", response = ErrorResponseDto.class)
  })
  ResponseEntity<SuccessResponseDto<ProductDto>> createProduct(
      @ApiParam(name = "Product payload", required = true) ProductCreateDto request);

}
