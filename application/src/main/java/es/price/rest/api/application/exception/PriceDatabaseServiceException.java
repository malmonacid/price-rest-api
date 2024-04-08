package es.price.rest.api.application.exception;

public class PriceDatabaseServiceException extends RuntimeException {
  public PriceDatabaseServiceException(Throwable cause) {
    super(cause);
  }

  public PriceDatabaseServiceException(String msg) {
    super(msg);
  }

}
