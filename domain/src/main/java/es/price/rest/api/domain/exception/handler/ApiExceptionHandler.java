package es.price.rest.api.domain.exception.handler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.price.rest.api.domain.exception.ProductNotFoundException;
import es.price.rest.api.domain.model.ApiError;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiError> handleApiException(ProductNotFoundException ex) {
    ApiError response = new ApiError(OffsetDateTime.now(), 404, ex.getMessage(), "/prices");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

}
