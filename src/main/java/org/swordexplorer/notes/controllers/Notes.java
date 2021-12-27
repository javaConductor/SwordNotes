package org.swordexplorer.notes.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.swordexplorer.notes.exceptions.NoDataFoundException;
import org.swordexplorer.notes.model.BibleTopic;
import org.swordexplorer.notes.service.TopicService;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController("/api/notes")
@RequestMapping("/api/notes")
public class Notes {

  private final TopicService topicService;

  @Value("${server.port} 8080")
  int port;

  @Autowired
  public Notes(TopicService topicService) {
    this.topicService = topicService;
  }

  @ApiOperation(value = "Read all topics")
  @ApiResponses({
    @ApiResponse(code = 200, message = "The request has succeeded. The client can read the response."),
    @ApiResponse(code = 500, message = "The request failed because of an unknown server error. Please report the error to the team.")})
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BibleTopic>> getAllTopics() {
    List<BibleTopic> topics = topicService.getTopics();
    log.info("/api/notes returning: {} topics.", topics.size());
    return ResponseEntity.ok().body(topics);
  }

  @ApiOperation(value = "Read a topic by Id")
  @ApiResponses({
    @ApiResponse(code = 200, message = "The request has succeeded. The client can read the response."),
    @ApiResponse(code = 400, message = "The request was not understood by the server. The response body contains more information."),
    @ApiResponse(code = 404, message = "The requested notes could not be found."),
    @ApiResponse(code = 500, message = "The request failed because of an unknown server error. Please report the error to the team.")})
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<BibleTopic> getTopic(@PathVariable long id) throws Exception{
    Optional<BibleTopic> possibleNotes = topicService.getTopic(id);
    return ResponseEntity.ok().body(possibleNotes.orElseThrow(() -> new NoDataFoundException("Could not find notes: " + id)));
  }

  @ApiOperation(value = "Create Topic")
  @ApiResponses({
    @ApiResponse(code = 201, message = "The request has succeeded. Notes have been created."),
    @ApiResponse(code = 400, message = "The request was not understood by the server. The response body contains more information."),
    @ApiResponse(code = 500, message = "The request failed because of an unknown server error. Please report the error to the team.")})
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public ResponseEntity<BibleTopic> createTopic(@Valid @RequestBody BibleTopic bibleTopic) {
    BibleTopic notes = topicService.createTopic(bibleTopic);
    //TODO change to https
    URI uri = URI.create(String.format("http://%s:%d/api/notes/%d", getHost(), port, notes.getId()));
    return ResponseEntity.created(uri).body(notes);
  }

  String getHost(){
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "localhost";
    }
  }

}
