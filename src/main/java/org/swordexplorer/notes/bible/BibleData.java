package org.swordexplorer.notes.bible;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lcollins on 5/11/2014.
 */
@Data
@AllArgsConstructor
 class BibleData {
  private List<Book> books;
  private Map<String, Verse> verses;
  private List<Chapter> chapters;

  public List<Book> getBooks() {
    return books;
  }

  public BibleData setBooks(List<Book> books) {
    this.books = books;
    return this;
  }

  public Map<String, Verse> getVerses() {
    return verses;
  }

  public BibleData setVerses(Map<String, Verse> verses) {
    this.verses = verses;
    return this;
  }

  public List<Chapter> getChapters() {
    return chapters;
  }

  public BibleData setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
    return this;
  }

  static Optional<BibleData> load(String jsonFilename) {
    InputStream stream = BibleData.class.getResourceAsStream(jsonFilename);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return Optional.of(objectMapper.readValue(stream, BibleData.class));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

}
