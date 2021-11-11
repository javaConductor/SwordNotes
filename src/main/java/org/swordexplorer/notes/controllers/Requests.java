package org.swordexplorer.notes.controllers;

import lombok.Data;
import org.swordexplorer.notes.model.BibleTopic;

public class Requests {


  @Data
  public class CreateNotesRequest {
    String username;
    BibleTopic notes;
  }

}
