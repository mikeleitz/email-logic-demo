package com.mikeleitz.email.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author leitz@mikeleitz.com
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="Email address not valid.")
public class InvalidEmailException extends Exception {
  private final static String EXCEPTION_MESSAGE = "[%s] is not a valid email address.";

  public InvalidEmailException(String email) {
    super(String.format(EXCEPTION_MESSAGE, email));
  }
}
