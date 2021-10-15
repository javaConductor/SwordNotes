package org.swordexplorer.notes.bible;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

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

//  BibleData(List<Book> books, Map<String, Verse> verses, List<Chapter> chapters) {
//    this.books = books
//    this.verses = verses
//    this.chapters = chapters
//  }
//
//  List<Book> getBooks() {
//    return books
//  }
//
//  void setBooks(List<Book> books) {
//    this.books = books
//  }
//
//  Map<String, Verse> getVerses() {
//    return verses
//  }
//
//  void setVerses(Map<String, Verse> verses) {
//    this.verses = verses
//  }
//
//  List<Chapter> getChapters() {
//    return chapters
//  }
//
//  void setChapters(List<Chapter> chapters) {
//    this.chapters = chapters
//  }

  static Optional<BibleData> load(String jsonFilename) {
    InputStream stream = BibleData.class.getResourceAsStream(jsonFilename);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      BibleData bibleData = objectMapper.readValue(stream, BibleData.class);
      return Optional.of(bibleData);
    } catch (IOException e) {
      return Optional.empty();
    }
  }

}
