package fr.carrefour.kata.core.exception;

import lombok.Getter;

import java.util.List;

public class EntityNotValidException extends RuntimeException {

  @Getter
  private ErrorCode code;
  @Getter
  private List<String> errors;

  public EntityNotValidException(String message) {
    super(message);
  }

  public EntityNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityNotValidException(String message, Throwable cause, ErrorCode errorCode) {
    super(message, cause);
    this.code = errorCode;
  }

  public EntityNotValidException(String message, ErrorCode errorCode, List<String> errors) {
    super(message);
    this.code = errorCode;
    this.errors = errors;
  }

}
