package com.swordexplorer.notes.security.repo;

import com.swordexplorer.notes.security.model.NotesUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<NotesUser, Long> {
    NotesUser findByUsername(String username);
}
