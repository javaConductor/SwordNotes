package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.service.TopicService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = NotesApplication.class)
@AutoConfigureMockMvc
public class BasicTest {


  @Autowired
  private TopicService topicService;

  @Autowired
  private Loader loader;


  @Test
  public void basicTest1() throws Exception {
    List<BibleTopic> topics = topicService.getTopics();
    assertEquals(0, topics.size());
  }
}
