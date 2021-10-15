package org.swordexplorer.notes.bible;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by lcollins on 5/10/2014.
 */
@Data
public class Verse implements Serializable {
  @Id
  private String verseId;

//    public String getVerseId() {
//        return verseId;
//    }
//
//    public void setVerseId(String verseId) {
//        this.verseId = verseId;
//    }
//
//    public int getVerse() {
//        return verse;
//    }
//
//    public void setVerse(int verse) {
//        this.verse = verse;
//    }
//
//    public int getBook() {
//        return book;
//    }
//
//    public void setBook(int book) {
//        this.book = book;
//    }
//
//    public int getChapter() {
//        return chapter;
//    }
//
//    public void setChapter(int chapter) {
//        this.chapter = chapter;
//    }
//
//    public String getVerseSpec() {
//        return verseSpec;
//    }
//
//    public void setVerseSpec(String verseSpec) {
//        this.verseSpec = verseSpec;
//    }
//
//    public String getVerseText() {
//        return verseText;
//    }
//
//    public void setVerseText(String verseText) {
//        this.verseText = verseText;
//    }
  private int verse;
  private int book;
  private int chapter;
  private String verseSpec;
  private String verseText;

  public String toString() {
    return getVerseSpec() + " " + getVerseText();
  }
}
