package org.swordexplorer.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.model.Note;
import org.swordexplorer.notes.model.repository.BibleTopicRepository;
import org.swordexplorer.notes.model.repository.NoteRepository;
import org.swordexplorer.notes.security.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
  private final BibleTopicRepository bibleTopicRepository;
  private final NoteRepository noteRepository;

  @Autowired
  UserService userService;

  @Autowired
  public TopicService(final BibleTopicRepository bibleTopicRepository, final NoteRepository noteRepository) {
  this.bibleTopicRepository = bibleTopicRepository;
  this.noteRepository = noteRepository;
  }

  public Optional<BibleTopic> getTopic(long id){
    BibleTopic n = bibleTopicRepository.getById(id);
    return Optional.of(n);
  }

  public BibleTopic createTopic(BibleTopic bibleTopic) {
    // validate verseSpec
    List<Note> savedNotes = bibleTopic.getNotes().stream()
      .map(noteRepository::save)
      .collect(Collectors.toList());
    bibleTopic.setNotes(savedNotes);
    return bibleTopicRepository.saveAndFlush(bibleTopic);
  }

  public List<BibleTopic> getTopics() {
    return bibleTopicRepository.findAll();
  }
}
