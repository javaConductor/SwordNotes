package org.swordexplorer.notes.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserResourceTest {


  @Autowired
  MockMvc mvc;

  @Autowired
  private UserResource userResource;

  public void jj() throws Exception {

    String output = mvc.perform(get("/api/users"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(content().json("[{\"id\":1,\"name\":\"John Travolta\",\"username\":\"john\",\"password\":\"$2a$10$yrjTT/KNrj9BvudmCPuPwO.dsqG3eeXq14NezdMGU32iQmZd5Uzum\",\"roles\":[{\"id\":4,\"name\":\"ROLE_USER\"}]"))
      .andReturn()
      .getResponse()
      .getContentAsString();

    log.info("Output: {}", output);


  }
}
