package org.swordexplorer.notes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Note {
  @NotNull String verseSpec;
  @NotNull
  String comments;
  boolean isBibleVerse;
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
