package org.swordexplorer.notes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BibleTopic {

  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long id;

  @NotNull
  private String username;

  @NotNull
  private String title;

//  @NotNull
  @OneToMany
  private List<Note> notes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public BibleTopic setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public BibleTopic setTitle(String title) {
    this.title = title;
    return this;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public BibleTopic setNotes(List<Note> notes) {
    this.notes = notes;
    return this;
  }
}
