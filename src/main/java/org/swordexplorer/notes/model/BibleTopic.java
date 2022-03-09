package org.swordexplorer.notes.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Eager
@ToString
public class BibleTopic {

  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String author;
  @NotNull
  private String username;
  @NotNull
  private String title;
  @NotNull
  @Column(name = "topic_date", nullable = false)
  private Date topicDate;
  @NotNull
  @Column(name = "created_at", nullable = false)
  private Date createdAt;
  @NotNull
  @Column(name = "created_by", nullable = false)
  private String createdBy;
  @NotNull
  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;
  @NotNull
  @Column(name = "updated_by", nullable = false)
  private String updatedBy;
  @OneToMany(fetch = FetchType.EAGER)
  private List<Note> notes = List.of();

  public BibleTopic(BibleTopic bibleTopic) {
    this.setTopicDate(bibleTopic.getTopicDate());
    this.setAuthor(bibleTopic.getAuthor());
    this.setUsername(bibleTopic.getUsername());
    this.setTitle(bibleTopic.getTitle());
    this.setCreatedAt(bibleTopic.getCreatedAt());
    this.setCreatedBy(bibleTopic.getCreatedBy());
    this.setUpdatedAt(bibleTopic.getUpdatedAt());
    this.setUpdatedBy(bibleTopic.getUpdatedBy());
    this.setNotes(bibleTopic.getNotes());
    this.setNotes(bibleTopic.getNotes().stream().collect(Collectors.toList()));
    this.setId(bibleTopic.getId());
  }

  public Long getId() {
    return id;
  }

  public BibleTopic setId(Long id) {
    this.id = id;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public BibleTopic setAuthor(String author) {
    this.author = author;
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

  public Date getTopicDate() {
    return topicDate;
  }

  public BibleTopic setTopicDate(Date topicDate) {
    this.topicDate = topicDate;
    return this;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public BibleTopic setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public BibleTopic setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public BibleTopic setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public BibleTopic setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
    return this;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public BibleTopic setNotes(List<Note> notes) {
    this.notes = notes;
    return this;
  }

  public static final class Builder {

    private Long id;
    private String author;
    //    private String username;
    private String title;
    private Date topicDate;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private List<Note> notes = List.of();


    private Builder() {
    }

    public static Builder aBibleTopic() {
      return new Builder();
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder author(String author) {
      this.author = author;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder topicDate(Date topicDate) {
      this.topicDate = topicDate;
      return this;
    }

    public Builder createdAt(Date createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder createdBy(String createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    public Builder updatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder updatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
      return this;
    }

    public Builder notes(List<Note> notes) {
      this.notes = notes;
      return this;
    }

    public BibleTopic build() {
      BibleTopic bibleTopic = new BibleTopic();
      bibleTopic.author = this.author;
      bibleTopic.createdAt = this.createdAt;
      bibleTopic.createdBy = this.createdBy;
      bibleTopic.updatedAt = this.updatedAt;
      bibleTopic.updatedBy = this.updatedBy;
      bibleTopic.id = this.id;
      bibleTopic.notes = this.notes;
      bibleTopic.title = this.title;
      bibleTopic.topicDate = this.topicDate;
      return bibleTopic;
    }
  }

}
