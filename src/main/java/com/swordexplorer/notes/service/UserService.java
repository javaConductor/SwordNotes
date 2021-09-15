package com.swordexplorer.notes.service;

import com.swordexplorer.notes.security.model.NotesUser;

import java.util.List;

import com.swordexplorer.notes.security.model.Role;

public interface UserService {
    NotesUser saveUser(NotesUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    NotesUser getUser(String username);
    List<NotesUser> getUsers();

    
}
