package org.swordexplorer.notes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swordexplorer.notes.bible.BibleService;
import org.swordexplorer.notes.exceptions.BadVerseSpecException;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.model.Note;
import org.swordexplorer.notes.model.repository.BibleTopicRepository;
import org.swordexplorer.notes.model.repository.NoteRepository;
import org.swordexplorer.notes.security.service.UserService;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicService {
  private final BibleTopicRepository bibleTopicRepository;
  private final NoteRepository noteRepository;
  private final BibleService bibleService;
  private final UserService userService;
  private final EntityManager em;

  @Autowired
  public TopicService(final BibleTopicRepository bibleTopicRepository,
                      final NoteRepository noteRepository,
                      @Named("bibleService") final BibleService bibleService,
                      @Named("userService") final UserService userService,
                      final EntityManager em) {
    this.bibleTopicRepository = bibleTopicRepository;
    this.noteRepository = noteRepository;
    this.bibleService = bibleService;
    this.userService = userService;
    this.em = em;
  }

  public Optional<BibleTopic> getTopic(long id) {
    log.info("getTopic:: {}", id);
    BibleTopic //n = bibleTopicRepository.getById(id);
      n = em.find(BibleTopic.class, id);
    return n == null ? Optional.empty() : Optional.of(n);
  }

  public BibleTopic createTopic(BibleTopic bibleTopic) {
    //TODO validate verseSpec isVerseSpec==true
    List<Note> savedNotes = bibleTopic.getNotes().stream()
      .map(this::saveNote)
      .collect(Collectors.toList());
    bibleTopic.setNotes(savedNotes);
    log.info("createTopic:: {}", bibleTopic);
    return bibleTopicRepository.saveAndFlush(bibleTopic);
  }

  public List<BibleTopic> getTopics() {
    return bibleTopicRepository.findAll();
  }

  ////// Notes ///////
  public Note saveNote(@Valid Note note) {
    log.info("saveNote:: {}", note);
    if (!validateVerseSpec(note.getVerseSpec()))
      throw new BadVerseSpecException(note.getVerseSpec());
    return noteRepository.save(note);
  }

  public boolean validateVerseSpec(String verseSpec) {
    log.info("validateVerseSpec:: {}", verseSpec);
    return bibleService.verseSpecToVerses(verseSpec) != null;
  }

}
