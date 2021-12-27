package org.swordexplorer.notes.bible;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.util.CollectionUtils.lastElement;

@Data
@AllArgsConstructor
class Pair {
  Integer start, end;
}

@Data
@Component
public class SimpleBibleService implements BibleService {
  private org.swordexplorer.notes.bible.BibleData bibleData;
  private List<Book> bookList;
  private List<Chapter> chapters;
  private Map<String, Verse> verses;

  public SimpleBibleService( @Value("${sword.bibletext.filename}") String jsonBibleTextFilename) {
    init(jsonBibleTextFilename);
  }

  public Optional<BibleData> init(String jsonBibleTextFilename) {
    return  BibleData.load(jsonBibleTextFilename)
      .map(SimpleBibleService::addVerseCountsToBooks)
      .stream().findFirst();
  }

  private static BibleData addVerseCountsToBooks(@NonNull BibleData bibleData) {
    List<Chapter> chapters = bibleData.getChapters();
    List<Book> books = bibleData.getBooks();
    Map<String, Verse> verses = bibleData.getVerses();
    books = books.stream()
      .map(book -> SimpleBibleService.addVerseCountsToBook(book, chapters))
      .collect(Collectors.toList());

    return new BibleData(books, verses, chapters);
  }

  private static Book addVerseCountsToBook(Book book, List<Chapter> chapters) {
    // init list to hold the counts
    // Start at chapter-1
    List<Integer> counts = chapters.stream()
      .filter(chapter -> chapter.book == book.getId())
      .sorted((o1, o2) -> {
        if (o1.getBook() == o2.getBook()) {
          return o1.getChapter() - o2.getChapter();
        }
        return o1.getBook() - o2.getBook();
      })
      .map(Chapter::getVerses)
      .collect(Collectors.toList());
    return new Book(book.getId(), book.getTitle(), book.getNumberOfChapters(), counts);
  }

  @Override
  public Integer chapterVerseCount(final int bkId, final int chapter) {
    return getBook(bkId).getNumberOfVersesInChapters().get(chapter - 1);
  }

  @Override
  public boolean isVerseSpec(String verseSpec) {
    if (!verseSpec.contains(":"))
      return false;

//    String[] bookAndVerses = verseSpec.split(" ");
//    String verses = bookAndVerses[1];
//
    Pattern regexBkChpt = Pattern.compile("(\\d*\\s*\\w+)\\s+(\\d{1,3})");
    Pattern regexVerse = Pattern.compile("(([\\d]{1,3})-*((\\d{1,3}+)[,]*\\s*)*)");

    String[] both = verseSpec.split(":");
    Matcher bkChptMatcher = regexBkChpt.matcher(both[0]);
    if (!bkChptMatcher.matches())
      return false;

    if (both.length == 1)
      return false;

    both[1] = both[1].replace(" ", "");
    List<String> verseSets = Arrays.asList(both[1].split(","));
    // find all the sets that do NOT match the pattern
    return verseSets.stream().noneMatch(vs -> (regexVerse.matcher(vs).matches()));
  }

  public String optimizeVerseSpecVerses(String versesStr) {

    List<String> parts = Arrays.asList(versesStr.split(","));
    List<Integer> verseNumberList = parts.stream()
      .flatMap(v -> {
        List<String> verseNums = Arrays.asList(v.split("-").clone());
        if (verseNums.size() == 1) {
          return List.of(Integer.parseInt(verseNums.get(0))).stream();
        } else {
          return IntStream
            .range(Integer.parseInt(verseNums.get(0)), Integer.parseInt(verseNums.get(1)))
            .boxed();
        }
      }).collect(Collectors.toList());

    List<Pair> pairs = new ArrayList<>();
    verseNumberList.forEach(n -> {
      if (pairs.isEmpty()) {
        pairs.add(new Pair(0, n));
      } else {
        Pair last = lastElement(pairs);
        int start = last.start;
        int end = last.end;
        if (n != end + 1) {
          pairs.add(new Pair(n, n));
        } else {
          pairs.add(new Pair(start, n));
        }
      }
    });

    return pairs.stream()
      .map(pair -> {
        if (pair.getStart().equals(pair.getEnd()))
          return String.format("%d", pair.getStart());
        else
          return String.format("%d-%d", pair.getStart(), pair.getEnd());
      })
      .collect(Collectors.joining(", "));
  }

  @Override
  public Map<String, Object> parseVerseSpec(String verseSpec) {
    if (!isVerseSpec(verseSpec))
      // return new HashMap<String, Object>();
      return Map.of();
    String[] parts = verseSpec.split(":");
    List<String> bkChpt = Arrays.asList(parts[0].split(" "));
    int chapter = Integer.parseInt(bkChpt.get(0));
    String bkName = String.join(" ", bkChpt).trim();
    // if bad book name
    return bookNameToBook(bkName)
      .map(book -> {
        String verses1 = (parts.length == 1) ? String.format("1-%d", chapterVerseCount(book.getId(), chapter)) : parts[1];
        Map<String, Object> m = new HashMap<>();
        m.put("book", book);
        m.put("chapter", chapter);
        // optimize verses
        m.put("verses", optimizeVerseSpecVerses(verses1));
        return m;
      }).orElse(Map.of());
  }

  @Override
  public VerseRange verseSpecToVerses(String verseSpec) {      /// break the string into parts starting with Bookname
    ///  Gen 5:2-6, 8-12
    Map<String, Object> parsedVerseSpec = parseVerseSpec(verseSpec);
    if (parsedVerseSpec.isEmpty()) {
      return null;
    }
    Book book = (Book) parsedVerseSpec.get("book");
    String bkName = book.getTitle();
    int chapter = (Integer) parsedVerseSpec.get("chapter");
    List<String> sets = Arrays.asList(parsedVerseSpec.get("verses").toString().split(","));
    // go through the sets of verses creating a list of int verse numbers
    List<Integer> verseNumbersList = sets.stream()
      .flatMap((vlist) -> {
        int start, end;
        if (vlist.contains("-")) {
          String[] l = vlist.split("-");
          start = Integer.parseInt(l[0]);
          end = Integer.parseInt(l[1]);

        } else {
          end = start = Integer.parseInt(vlist);
        }
        return IntStream.range(start, end)
          .boxed();
      }).collect(Collectors.toList());

    /// Get the verses related to the verseNumbers
    List<Verse> verses = verseNumbersList.stream()
      .flatMap(vNum -> {
        String vid = String.format("%02d%03d%03d", book.getId(), chapter, vNum);
        Verse verse = getVerse(vid).orElse(null);
        return (verse != null)
          ? List.of(verse).stream()
          : new ArrayList<Verse>().stream();
      })
      .collect(Collectors.toList());
    return new VerseRange(String.format("%s %s:%s", bkName, chapter, parsedVerseSpec.get("verses")), verses);
  }

  @Override
  public Optional<Book> bookNameToBook(@NonNull final String bkName) {
    Book theBook = null;
    String bkNameUpper = bkName.toUpperCase();

    //find exact match
    theBook = this.bookList.stream().filter(book -> book.getTitle().toUpperCase().equals(bkNameUpper))
      .findFirst().orElse(null);

    if (theBook == null) {
      // find a unique partial match
      List<Book> list = this.bookList.stream().filter(book -> book.getTitle().toUpperCase().startsWith(bkNameUpper.toUpperCase()))
        .collect(Collectors.toList());
      if (list.size() == 1)
        theBook = list.get(0);
    }
    return Optional.of(theBook);
  }

  @Override
  public Optional<Verse> getVerse(String verseId) {
    final Verse v = bibleData.getVerses().get(verseId);
    return Optional.of(v);
  }

  @Override
  public List<Verse> getVerses(List<String> verseIds) {
    return verseIds.stream()
      .map(vid -> {
        Verse v = getVerse(vid).orElse(null);
        if (v != null) {
          v.setVerseSpec(verseSpecFromVerseId(vid));
        }
        return v;
      })
      .collect(Collectors.toList());
  }

  @Override
  public List<Verse> getVerses(String verseSpec) {
    return verseSpecToVerses(verseSpec).getVerses();
  }

  @Override
  public VerseRange getChapter(int book, int chapter) {
    final Book bk = getBook(book);
    Integer lastVerse = chapterVerseCount(book, chapter);
    String verseSpec = String.format("%s %d:1-%d", bk.getId(), chapter, lastVerse);
    return verseSpecToVerses(verseSpec);
  }

  @Override
  public List<Book> getBooks() {
    return bibleData.getBooks();
  }

  @Override
  public Book getBook(int bookId) {
    return getBooks().get(bookId - 1);
  }

  @Override
  public String verseSpecFromVerseId(String vid) {

    String bkIdStr = vid.substring(0, 2);
    int bkId = Integer.parseInt(bkIdStr);
    if (bkId > 66 || bkId < 1)
      return null;

    String chapterStr = vid.substring(2, 5);
    int chapter = Integer.parseInt(chapterStr);

    String verseIdStr = vid.substring(0, 2);
    int verse = Integer.parseInt(verseIdStr);

    final Book book = getBook(bkId);
    if (book == null)
      return null;

    String bkName = book.getTitle();
    return bkName + " " + chapter + ":" + verse;
  }

  /**
   * @param phrase
   * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
   */
  @Override
  public List<Verse> getVersesWithPhrase(final String phrase) {
    return bibleData.getVerses().values().stream()
      .filter(verse -> wordIsInVerse(verse, phrase))
      .collect(Collectors.toList());
  }

  /**
   * @param words
   * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
   */
  public List<Verse> getVersesWithAllWords(List<String> words) {
    return bibleData.getVerses().values().stream()
      .filter(verse -> words.stream()
        .allMatch(w -> wordIsInVerse(verse, w)))
      .collect(Collectors.toList());
  }//all

  boolean wordIsInVerse(Verse verse, String word) {
    if (word == null)
      return false;
    Pattern wordPattern = Pattern.compile("^.*[^\\w]+($*" + word.toUpperCase() + "})[ ,.:;$]+.*");
    Matcher matcher = wordPattern.matcher(verse.getVerseText());
    return matcher.matches();
  }

  /**
   * @param words
   * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
   */
  public List<Verse> getVersesWithAnyWords(List<String> words) {
    return bibleData.getVerses().values().stream()
      .filter(verse -> words.stream()
        .anyMatch(w -> wordIsInVerse(verse, w)))
      .collect(Collectors.toList());
  }

  public List<String> parseWords(String input) {
    return Arrays.stream(input.split(" "))
      .filter(w -> w.trim().length() > 0)
      .collect(Collectors.toList());
  }

  /**
   * @param searchText
   * @param searchType
   * @return searchResults[{verseId:abc, verseSpec:xyz, verseText:123}]
   */
  @Override
  public List<Verse> searchVerses(String searchText, String searchType) {
    List<String> wordList = parseWords(searchText);
    switch (searchType.toUpperCase()) {
      case "ANY":
        return getVersesWithAnyWords(wordList);
      case "ALL":
        return getVersesWithAllWords(wordList);
      case "PHRASE":
        return getVersesWithPhrase(searchText);
      default:
        throw new IllegalArgumentException("searchType must be one of: ALL, NAY, PHRASE");
    }
  }

  public Object getBibleData() {
    return bibleData;
  }

  public void setBibleData(BibleData bibleData) {
    this.bibleData = bibleData;
  }

  public List<Book> getBookList() {
    return bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }

  public Object getChapters() {
    return chapters;
  }

  public void setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
  }

  public Object getVerses() {
    return verses;
  }

  public void setVerses(Map<String, Verse> verses) {
    this.verses = verses;
  }

  SearchResult verseToSearchResult(Verse verse) {
    String verseId = verse.getVerseId();
    String verseText = verse.getVerseText();
    return new SearchResult(verseId, verseSpecFromVerseId(verseId), verseText);
  }

}
