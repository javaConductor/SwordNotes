package org.swordexplorer.notes.exceptions;

public class BadVerseSpecException extends RuntimeException {
  public BadVerseSpecException(String badVerseSpec) {
    super(String.format("bad verse spec: [%s]", badVerseSpec));
  }
}
