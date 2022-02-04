package org.swordexplorer.notes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.swordexplorer.notes.controllers.Notes;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = Notes.class)
@SpringBootTest(classes = Notes.class)
class NotesControllerTests {

  @Autowired
  private org.swordexplorer.notes.controllers.Notes controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }

}
