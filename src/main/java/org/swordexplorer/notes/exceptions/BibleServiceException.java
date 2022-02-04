package org.swordexplorer.notes.exceptions;

public class BibleServiceException extends RuntimeException {
  public BibleServiceException(String message) {
    super(String.format("bible service error: [%s]", message));
  }
}
