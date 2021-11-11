package org.swordexplorer.notes.security.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.swordexplorer.notes.security.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
