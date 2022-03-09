package org.swordexplorer.notes.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
public class TopicService {
  private final BibleTopicRepository bibleTopicRepository;
  private final NoteRepository noteRepository;
  private final BibleService bibleService;
  private final UserService userService;
  private final EntityManager em;
  private Session session;

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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
  public Optional<BibleTopic> getTopic(long id) {
    log.info("getTopic:: {}", id);

    BibleTopic n = bibleTopicRepository.getById(id);
    n = (BibleTopic) Hibernate.unproxy(n);
    return n == null ? Optional.empty() : Optional.of(n);
  }

  public BibleTopic createTopic(BibleTopic bibleTopic) {
    //TODO validate verseSpec isVerseSpec==true
    List<Note> savedNotes = bibleTopic.getNotes().stream()
      .map(this::saveNote)
      .collect(Collectors.toList());
    bibleTopic.setNotes(savedNotes);
    log.info("createTopic:: {}", bibleTopic);
    BibleTopic newTopic = bibleTopicRepository.saveAndFlush(bibleTopic);
    newTopic = (BibleTopic) Hibernate.unproxy(newTopic);
    return newTopic;
  }

  public List<BibleTopic> getTopics() {
    return bibleTopicRepository.findAll();
  }

  ////// Notes ///////
  public Note saveNote(@Valid Note note) {
    log.info("saveNote:: {}", note);
    if (!validateVerseSpec(note.getVerseSpec()))
      throw new BadVerseSpecException(note.getVerseSpec());
    Note savedNote = noteRepository.save(note);
    return (Note) Hibernate.unproxy(savedNote);
  }

  public boolean validateVerseSpec(String verseSpec) {
    log.info("validateVerseSpec:: {}", verseSpec);
    return bibleService.verseSpecToVerses(verseSpec) != null;
  }

}
