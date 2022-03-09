package org.swordexplorer.notes.bible;

import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public interface BibleService {
  Integer chapterVerseCount(final int bkId, final int chapter);

  Map<String, Object> parseVerseSpec(String verseSpec);

  boolean isVerseSpec(String verseSpec);

  VerseRange verseSpecToVerses(String verseSpec);

  Optional<Book> bookNameToBook(@NonNull final String bkName);

  Optional<Verse> getVerse(String verseId);

  List<Verse> getVerses(List<String> verseIds);

  List<Verse> getVerses(String verseSpec);

  VerseRange getChapter(int book, int chapter);

  List<Verse> getVersesWithPhrase(String phrase);

  List<Verse> getVersesWithAnyWords(List<String> words);

  List<Verse> getVersesWithAllWords(List<String> words);

  List<Book> getBooks();

  org.swordexplorer.notes.bible.Book getBook(int bookId);

  String verseSpecFromVerseId(String vid);

  List<Verse> searchVerses(String searchText, String searchType);
}
