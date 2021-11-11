package org.swordexplorer.notes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Note {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long id;

  @NotNull String verseSpec;

  @NotNull
  String comments;

}
