package org.swordexplorer.notes.bible;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lcollins on 5/11/2014.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
