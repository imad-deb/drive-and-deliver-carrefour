package fr.carrefour.kata.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  CUSTOMER_NOT_VALID(1000),
  DELIVERY_NOT_VALID(2000);

  private int code;
}
