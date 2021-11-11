package org.swordexplorer.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDataFoundException extends RuntimeException {
  public NoDataFoundException(String message) {
    super(message);
  }

  public NoDataFoundException() {
    this("no data found");
  }
}
