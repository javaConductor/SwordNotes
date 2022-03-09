package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swordexplorer.notes.bible.BibleService;
import org.swordexplorer.notes.bible.SimpleBibleService;

@SpringBootApplication
@Slf4j
public class NotesApplication {

  public final static String MANIFEST_FILENAME = ".topics.manifest";
  private String jsonBibleTextFilename;

  public NotesApplication(@Value("${sword.bibletext.filename}") String jsonBibleTextFilename) {
    this.jsonBibleTextFilename = jsonBibleTextFilename;
  }

  public static void main(String[] args) {
    SpringApplication.run(NotesApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  BibleService getBibleService() {
    return new SimpleBibleService(jsonBibleTextFilename);
  }
}
