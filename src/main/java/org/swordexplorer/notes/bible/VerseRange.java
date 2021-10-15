package org.swordexplorer.notes.bible;

import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A VerseSpec and its verses
 */
@ToString
public class VerseRange implements Serializable {
  private String verseSpec;
  private List<Verse> verses;

  public VerseRange(String verseSpec, List<Verse> verses) {
    this.verseSpec = verseSpec;
    this.verses = verses;
  }

  public static VerseSet asVerseSet(VerseRange vr) {
    return new VerseSet(vr.verseSpec, vr.getVerses().stream()
      .map((verse -> verse.getVerseId())).collect(Collectors.toList()));
  }

  public String getVerseSpec() {
    return verseSpec;
  }

  public void setVerseSpec(String verseSpec) {
    this.verseSpec = verseSpec;
  }

  public List<Verse> getVerses() {
    return verses;
  }

  public void setVerses(List<Verse> verses) {
    this.verses = verses;
  }
}
