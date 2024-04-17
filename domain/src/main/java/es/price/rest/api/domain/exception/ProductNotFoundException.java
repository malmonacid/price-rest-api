package es.price.rest.api.domain.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Throwable cause) {
    super(cause);
  }

  public ProductNotFoundException(String msg) {
    super(msg);
  }

}
