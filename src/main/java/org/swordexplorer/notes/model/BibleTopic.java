package org.swordexplorer.notes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BibleTopic {

  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long id;

  @NotNull
  private String author;

  @NotNull
  private String username;

  @NotNull
  private String title;

  @NotNull
  @Column(name = "created_at", nullable = false)
  private Date date;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Note> notes = List.of();

  public Date getDate() {
    return date;
  }

  public BibleTopic setDate(Date date) {
    this.date = date;
    return this;
  }

  public Long getId() {
    return id;
  }

  public BibleTopic setId(Long id) {
    this.id = id;
    return this;
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
  public String getAuthor() {
    return author;
  }

  public BibleTopic setAuthor(String author) {
    this.author = author;
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
