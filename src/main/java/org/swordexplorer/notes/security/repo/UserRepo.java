package org.swordexplorer.notes.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swordexplorer.notes.security.model.NotesUser;

public interface UserRepo extends JpaRepository<NotesUser, Long> {
  NotesUser findByUsername(String username);
}
