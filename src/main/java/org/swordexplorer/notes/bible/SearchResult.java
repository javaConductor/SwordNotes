package org.swordexplorer.notes.bible;

import lombok.AllArgsConstructor;

/**
 * Created by lee on 6/16/17.
 */
@AllArgsConstructor
public class SearchResult {
  private String verseId;
  private String verseSpec;
  private String verseText;

  public String getVerseId() {
    return verseId;
  }

  public void setVerseId(String verseId) {
    this.verseId = verseId;
  }

  public String getVerseSpec() {
    return verseSpec;
  }

  public void setVerseSpec(String verseSpec) {
    this.verseSpec = verseSpec;
  }

  public String getVerseText() {
    return verseText;
  }

  public void setVerseText(String verseText) {
    this.verseText = verseText;
  }
}
