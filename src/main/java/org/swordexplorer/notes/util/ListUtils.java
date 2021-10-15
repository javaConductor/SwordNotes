package org.swordexplorer.notes.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {

  public static <V> V listHead(List<V> list) {
    return list.iterator().next();
  }

  public static <V> List<V> listTail(List<V> list) {
    return list.stream()
      .skip(1)
      .collect(Collectors.toList());
  }

  public static <V> List<V> listReverse(List<V> list) {
    List<V> newList = new ArrayList<>();
    Collections.copy(newList, list);
    Collections.reverse(newList);
    return newList;
  }

  public static <V> List<V> listConcat(List<V> list1, List<V> list2) {
    return Stream.concat(list1.stream(), list2.stream())
      .collect(Collectors.toList());
  }

}
