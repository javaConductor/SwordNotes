package com.swordexplorer.notes.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
class Verse {
  Integer verse;
  Integer chapter;
  Integer book;

  @Id
  public String toString() {
    String s = String.format("%2d%3d%3d", book, chapter, verse);
    return s;
  }

  public Verse(Integer verse, Integer chapter, Integer book) {
    this.verse = verse;
    this.chapter = chapter;
    this.book = book;
  }


}

class VerseText extends Verse {
  String text;

  public VerseText(Integer verse, Integer chapter, Integer book, String text) {
    super(verse, chapter, book);
    this.text = text;
  }

}
