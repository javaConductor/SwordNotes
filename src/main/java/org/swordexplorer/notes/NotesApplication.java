package org.swordexplorer.notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swordexplorer.notes.security.model.NotesUser;
import org.swordexplorer.notes.security.model.Role;
import org.swordexplorer.notes.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class NotesApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotesApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CommandLineRunner run(UserService userService) {
    return args -> {

      userService.saveUser(new NotesUser(null, "John Travolta", "john", "1234", new ArrayList<>()));
      userService.saveUser(new NotesUser(null, "David Collins", "dave", "1120", new ArrayList<>()));
      userService.saveUser(new NotesUser(null, "Sadar Bahar", "sadar", "777", new ArrayList<>()));

      userService.saveRole(new Role(null, "ROLE_USER"));
      userService.saveRole(new Role(null, "ROLE_MANAGER"));
      userService.saveRole(new Role(null, "ROLE_ADMIN"));
      userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


      userService.addRoleToUser("john", "ROLE_USER");
      userService.addRoleToUser("john", "ROLE_MANAGER");
      userService.addRoleToUser("dave", "ROLE_USER");
      userService.addRoleToUser("dave", "ROLE_ADMIN");
      userService.addRoleToUser("sadar", "ROLE_USER");
      userService.addRoleToUser("sadar", "ROLE_SUPER_ADMIN");

    };
  }

  @Bean
  public String name() {
    return "name";
  }

}
