package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swordexplorer.notes.bible.BibleService;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.model.Note;
import org.swordexplorer.notes.service.TopicService;
import org.swordexplorer.notes.util.ListUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

@Service
@Slf4j
class Loader {

  List<String> currentManifest = List.of();
  private TopicService topicService;
  private BibleService bibleService;

  @Autowired
  protected Loader(TopicService topicService, BibleService bibleService) {
    this.bibleService = bibleService;
    this.topicService = topicService;
  }

  public List<Long> load(String directory) throws Exception {
    List<Long> newTopicIds = loadTopicsFromDirectory(directory);
    System.out.printf("Loaded %d new topic%s from directory: %s%n",
      newTopicIds.size(),
      (newTopicIds.size() != 1 ? "s" : ""),
      directory);
    return newTopicIds;
  }

  /**
   * @param directory
   * @return no of topics loaded
   */
  protected List<Long> loadTopicsFromDirectory(String directory) throws Exception {

    int filesToLoad = -1;
    List<Long> loadedNotesIds;
    try {
      // load manifest file from directory (if any)
      this.currentManifest = loadTopicManifest(directory);
      // create a list of CSV files in the dir
      List<String> topicFiles = loadTopicFileList(directory);
      filesToLoad = topicFiles.size();
      // for each file in the file list
      loadedNotesIds = topicFiles.stream()
        .filter(csvFile -> !this.currentManifest.contains(csvFile))// filter out ones already in the manifest
        .filter(file -> (file.endsWith("csv")))// filter out all but .csv
        .flatMap(newCsvFile -> {
          long newId;
          try {
            newId = loadTopicFromFile(new File(directory, newCsvFile));
            if (newId != -1) {
              addToManifest(directory, newCsvFile);
              return Stream.of(newId);
            }
          } catch (IOException e) {
            log.error("error error updating manifest file: {}", e.getMessage());
            log.error("error", e);
          } catch (Exception e) {
            log.error("error loading topic from file: {} {}", newCsvFile, e.getMessage());
            log.error("error", e);
          }
          return Stream.empty();
        }).collect(Collectors.toList());
    } catch (IOException e) {
      log.error("error loading topics from directory: {}: {}", directory, e.getMessage());
      return List.of();
    }

    log.info("Loaded " + loadedNotesIds.size() + " of " + filesToLoad + " files loaded from: " + directory);
    return loadedNotesIds;
  }

  protected long loadTopicFromFile(File f) throws Exception {
    String[] parts = null;

    try {
      /// open file
      List<String> lines = Files.lines(f.toPath()).collect(Collectors.toList());
      /// read first line
      String titleLine = lines.get(0);
      /// Split first line
      parts = titleLine.split(",");
      /// validate whether the second is a date
      java.util.Date date = parseDate(parts[1],
        "yyyy-MM-dd", "dd/MM/yyyy", "dd MMM yyyy");

      /// on error, log error and return -1
      /// create BibleTopic object
      BibleTopic topic = BibleTopic.Builder.aBibleTopic()
        .title(parts[2])
        .topicDate(new Date(date.getTime()))
        .createdBy("System")
        .createdAt(new Date(new java.util.Date().getTime()))
        .updatedBy("System")
        .updatedAt(new Date(new java.util.Date().getTime()))
        .author(parts[0])
        .build();

      List<Note> notes = new ArrayList<>();
      for (int lineNumber = 1; lineNumber < lines.size(); ++lineNumber) {
        String line = lines.get(lineNumber);
        String[] lineParts = line.split(",");
        Note note = new Note();
        String verseSpec = lineParts[1];
        String noteText = lineParts[2];
        note.setComments(noteText);
        note.setVerseSpec(verseSpec);
        boolean isVerseSpec = bibleService.isVerseSpec(verseSpec);
        note.setBibleVerse(isVerseSpec);
        notes.add(note);

        System.out.println("Note: " + note);
      }
      topic.setNotes(notes);

      /// for each of the rest of the lines
      //// validate whether the first field is empty
      //// validate whether the second field is a valid verse spec and mark as bible note
      //// on error, log line number and error
      //// create Note object
      /// Add Note objects to BibleTopic

      /// Save BibleTopic in DB
      topic = topicService.createTopic(topic);
      /// return BibleTopic ID
      System.out.println("loadTopicFromFile(" + f.getAbsolutePath() + ") called! Created: " + topic);
      return topic.getId();
    } catch (IOException e) {
      throw new Exception("error loading topic from:" + f.getAbsolutePath(), e);
    } catch (ParseException e) {
      throw new Exception("error parsing date [" + parts[1] + "]  loading topic from:" + f.getAbsolutePath(), e);
    }
  }

  protected List<String> loadTopicFileList(String dir) throws IOException {
    File f = new File(dir);
    File[] filesInDir = f.listFiles();
    if (filesInDir == null)
      throw new IOException("error loading topic from directory:" + f.getAbsolutePath() + "\nMay not be a directory.");
    return Stream.of(filesInDir)
      .filter(file -> !file.isDirectory())
      .filter(file -> (file.getName().endsWith("csv")))// filter out all but .csv
      .map(File::getName)
      .collect(Collectors.toList());
  }

  protected List<String> loadTopicManifest(String dir) throws IOException {
    File f = new File(dir, NotesApplication.MANIFEST_FILENAME);
    try {
      if (f.exists())
        return Files.readAllLines(f.toPath());
      f.createNewFile();
      return List.of();
    } catch (IOException e) {
      log.error("could not read Topic manifest file: " + f.getAbsolutePath());
      throw e;
    }
  }

  protected List<String> addToManifest(String dir, String filename) throws IOException {
    File f = new File(dir, NotesApplication.MANIFEST_FILENAME);
    try {
      FileWriter fw = new FileWriter(f.getAbsolutePath(), true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(filename);
      bw.newLine();
      bw.close();
      this.currentManifest = ListUtils.listConcat(this.currentManifest, List.of(filename));
      return this.currentManifest.stream().collect(Collectors.toList());
    } catch (IOException e) {
      log.error("could not append Topic manifest file: " + f.getAbsolutePath());
      throw e;
    }
  }
}
