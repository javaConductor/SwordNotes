package org.swordexplorer.notes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swordexplorer.notes.security.service.UserService;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class NotesApplication {

  public final static String MANIFEST_FILENAME = ".topics.manifest";

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

      Arrays.stream(args).peek(s -> {
        String text = String.format("args: [%s]", s);
        System.out.println(text);
//        return text;
      }).collect(Collectors.toList());

//      NotesUser nu = userService.saveUser(new NotesUser(null, "John Travolta", "john", "1234", new ArrayList<>()));
//      nu = userService.saveUser(new NotesUser(null, "David Collins", "dave", "1120", new ArrayList<>()));
//      nu = userService.saveUser(new NotesUser(null, "Sadar Bahar", "sadar", "777", new ArrayList<>()));
//
//      userService.saveRole(new Role(null, "ROLE_USER"));
//      userService.saveRole(new Role(null, "ROLE_MANAGER"));
//      userService.saveRole(new Role(null, "ROLE_ADMIN"));
//      userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//
//      userService.addRoleToUser("john", "ROLE_USER");
//      userService.addRoleToUser("john", "ROLE_MANAGER");
//      userService.addRoleToUser("dave", "ROLE_USER");
//      userService.addRoleToUser("dave", "ROLE_ADMIN");
//      userService.addRoleToUser("sadar", "ROLE_USER");
//      userService.addRoleToUser("sadar", "ROLE_SUPER_ADMIN");

    };
  }
//
//  @Bean
//  public String name() {
//    return "name";
//  }

}
