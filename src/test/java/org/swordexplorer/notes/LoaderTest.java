package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeComparator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.service.TopicService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = NotesApplication.class)
@AutoConfigureMockMvc
public class LoaderTest {

  @Autowired
  private TopicService topicService;

  @Autowired
  private Loader loader;

  @AfterEach
  public void removeManifestFile() throws Exception {
    Path resourceDirectory = Paths.get("src", "test", "resources");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();

    boolean ans = new File(absolutePath + "/topics-csv/test1/" + NotesApplication.MANIFEST_FILENAME).delete();
    ans = new File(absolutePath + "/topics-csv/test2/" + NotesApplication.MANIFEST_FILENAME).delete();
    ans = new File(absolutePath + "/topics-csv/badDate/" + NotesApplication.MANIFEST_FILENAME).delete();
  }

  @Test
  public void loadTestTopic1NoNotes() throws Exception {
    Path resourceDirectory = Paths.get("src", "test", "resources", "topics-csv", "test1");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<Long> newTopicIds = loader.load(absolutePath);

    assertEquals(1, newTopicIds.size());
    long addedId = newTopicIds.get(0);
    // check db
    Optional<BibleTopic> newTopicOpt = topicService.getTopic(addedId);
    assertTrue(newTopicOpt.isPresent());
    BibleTopic bibleTopic = newTopicOpt.get();

    assertEquals("This is a test Note", bibleTopic.getTitle());
    assertEquals("System", bibleTopic.getCreatedBy());
    assertNull( bibleTopic.getUsername());
    assertEquals("Test Author", bibleTopic.getAuthor());

    Date topicDate = bibleTopic.getTopicDate();
    Calendar expectedCal = Calendar.getInstance();
    expectedCal.set(2021, Calendar.NOVEMBER, 20, 0, 0, 0);
    Date expectedDate = expectedCal.getTime();
    log.info("expected Date: " + expectedDate);
    log.info("topicDate: " + topicDate);
    assertEquals(0, DateTimeComparator.getDateOnlyInstance().compare(expectedCal.getTime(), topicDate));
  }

  @Test
  public void loadTestTopic2() throws Exception {
    Path resourceDirectory = Paths.get("src", "test", "resources", "topics-csv", "test2");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<Long> newTopicIds = loader.load(absolutePath);

    assertEquals(2, newTopicIds.size());
    long addedId = newTopicIds.get(1);
    // check db
    Optional<BibleTopic> newTopicOpt = topicService.getTopic(addedId);
    assertTrue(newTopicOpt.isPresent());
    BibleTopic bibleTopic = newTopicOpt.get();

    assertEquals("This is another test Note", bibleTopic.getTitle());
    assertEquals("System", bibleTopic.getCreatedBy());
    assertEquals("Test Author", bibleTopic.getAuthor());

    Date topicDate = bibleTopic.getTopicDate();
    Calendar expectedCal = Calendar.getInstance();
    expectedCal.set(2021, Calendar.NOVEMBER, 28, 0, 0, 0);
    Date expectedDate = expectedCal.getTime();
    assertEquals(0, DateTimeComparator.getDateOnlyInstance().compare(expectedDate, topicDate));
  }

  @Test
  public void loadTestBadDate() throws Exception {
    Path resourceDirectory = Paths.get("src", "test", "resources", "topics-csv", "badDate");
    String absolutePath = resourceDirectory.toFile().getAbsolutePath();
    List<Long> newTopicIds = loader.load(absolutePath);

    assertEquals(0, newTopicIds.size());
  }

}
