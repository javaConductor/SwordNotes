package org.swordexplorer.notes.bible;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

import static org.swordexplorer.notes.util.ListUtils.listHead;

/**
 * Created by lcollins on 11/28/2014.
 */
public class VerseRangeComparator implements Comparator<VerseRange> {

  @Autowired
  private BibleService bibleService;

  @Override
  public int compare(VerseRange vr1, VerseRange vr2) {

    Book bk1 = vr1.getVerses().stream().findFirst()
      .map(verse -> verse.getBook())
      .map(bookIdx -> bibleService.getBook(bookIdx)).orElse(null);

    Book bk2 = vr2.getVerses().stream().findFirst()
      .map(verse -> verse.getBook())
      .map(bookIdx -> bibleService.getBook(bookIdx)).orElse(null);

    if (!bk1.equals(bk2)) {
      return bk2.getId() - bk1.getId();
    }

    int ch1 = vr1.getVerses().get(0).getChapter();
    int ch2 = vr2.getVerses().get(0).getChapter();

    if (ch1 != ch2) {
      return ch2 - ch1;
    }

    int startVerse1 = listHead(vr1.getVerses()).getVerse();
    int startVerse2 = listHead(vr2.getVerses()).getVerse();

    if (startVerse1 != startVerse2) {
      return startVerse2 - startVerse1;
    }

    return vr2.getVerses().size() - vr1.getVerses().size();
  }

}
