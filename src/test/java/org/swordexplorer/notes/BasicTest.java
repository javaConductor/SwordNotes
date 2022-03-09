package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.swordexplorer.notes.service.TopicService;

@Slf4j
@SpringBootTest(classes = NotesApplication.class)
@AutoConfigureMockMvc
public class BasicTest {


  @Autowired
  private TopicService topicService;

  @Autowired
  private Loader loader;

}
