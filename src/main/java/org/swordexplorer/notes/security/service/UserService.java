package org.swordexplorer.notes.security.service;

import org.swordexplorer.notes.security.model.NotesUser;
import org.swordexplorer.notes.security.model.Role;

import java.util.List;

public interface UserService {
  Role saveRole(Role role);

  void addRoleToUser(String username, String rolename);

  NotesUser saveUser(NotesUser user);

  NotesUser getUser(String username);

  NotesUser getUserById(Long id);

  List<NotesUser> getUsers();
}
