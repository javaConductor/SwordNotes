package org.swordexplorer.notes.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
class Verse {
  Integer verse;
  Integer chapter;
  Integer book;

  public Verse(Integer verse, Integer chapter, Integer book) {
    this.verse = verse;
    this.chapter = chapter;
    this.book = book;
  }

  @Id
  public String toString() {
    return String.format("%2d%3d%3d", book, chapter, verse);
  }


}

class VerseWithText extends Verse {
  String text;

  public VerseWithText(Integer verse, Integer chapter, Integer book, String text) {
    super(verse, chapter, book);
    this.text = text;
  }

}
