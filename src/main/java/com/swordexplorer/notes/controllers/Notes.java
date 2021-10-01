package com.swordexplorer.notes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Notes {
  @GetMapping(value = "/")
  public String index() {
    return "SwordExplorer Notes";
  }
}
