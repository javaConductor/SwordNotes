package org.swordexplorer.notes.api;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.swordexplorer.notes.security.model.NotesUser;
import org.swordexplorer.notes.security.model.Role;
import org.swordexplorer.notes.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<NotesUser>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @PostMapping(value = "/user/save")
  public ResponseEntity<NotesUser> saveUser(@RequestBody NotesUser user) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
    return ResponseEntity.created(uri).body(userService.saveUser(user));
  }

  @PostMapping(value = "/role/save")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }

  @PostMapping(value = "/role/addtouser")
  public ResponseEntity<Role> addRoleToUser(@RequestBody RoleToUserForm form, @RequestBody String username) {
    userService.addRoleToUser(form.getRoleName(), form.getUsername());
    return ResponseEntity.ok().build();
  }
}

@Data
class RoleToUserForm {
  private String username;
  private String roleName;
}
