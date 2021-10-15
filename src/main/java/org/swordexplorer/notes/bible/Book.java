package org.swordexplorer.notes.bible;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lcollins on 5/11/2014.
 */
@Data
class Book implements Serializable {
  int id;
  String title;
  int numberOfChapters;
  List<Integer> numberOfVersesInChapters;


  int versesInChapter(int chapter) {
    if (chapter > numberOfChapters)
      return -1;
    return numberOfVersesInChapters.get(chapter - 1);
  }

  Book(int id, String title, int numberOfChapters, List<Integer> numberOfVersesInChapters) {
    this.id = id;
    this.title = title;
    this.numberOfChapters = numberOfChapters;
    this.numberOfVersesInChapters = numberOfVersesInChapters;
  }
}
