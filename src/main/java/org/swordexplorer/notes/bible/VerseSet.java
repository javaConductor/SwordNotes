package org.swordexplorer.notes.bible;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class VerseSet implements Serializable {
  private Object verseSpec;
  private List<String> verseIds;

  public VerseSet(String verseSpec, List<String> verseIds) {
    this.verseSpec = verseSpec;
    this.verseIds = verseIds;
  }

  /**
   * Returns a string representation of the object. In general, the
   * {@code toString} method returns a string that
   * "textually represents" this object. The result should
   * be a concise but informative representation that is easy for a
   * person to read.
   * It is recommended that all subclasses override this method.
   * <p>
   * The {@code toString} method for class {@code Object}
   * returns a string consisting of the name of the class of which the
   * object is an instance, the at-sign character `{@code @}', and
   * the unsigned hexadecimal representation of the hash code of the
   * object. In other words, this method returns a string equal to the
   * value of:
   * <blockquote>
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre></blockquote>
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return ((String) (verseSpec));
  }

  public Object getVerseSpec() {
    return verseSpec;
  }

  public void setVerseSpec(Object verseSpec) {
    this.verseSpec = verseSpec;
  }

  public List<String> getVerseIds() {
    return verseIds;
  }

  public void setVerseIds(List<String> verseIds) {
    this.verseIds = verseIds;
  }
}
