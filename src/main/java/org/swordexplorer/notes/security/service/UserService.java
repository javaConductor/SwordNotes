package org.swordexplorer.notes.security.service;

import org.swordexplorer.notes.security.model.NotesUser;
import org.swordexplorer.notes.security.model.Role;

import java.util.List;

public interface UserService {
  NotesUser saveUser(NotesUser user);

  Role saveRole(Role role);

  void addRoleToUser(String username, String rolename);

  NotesUser getUser(String username);

  List<NotesUser> getUsers();
}
