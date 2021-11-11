package org.swordexplorer.notes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.swordexplorer.notes.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}
