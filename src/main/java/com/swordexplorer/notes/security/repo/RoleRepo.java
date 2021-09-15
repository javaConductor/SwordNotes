package com.swordexplorer.notes.security.repo;

import com.swordexplorer.notes.security.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long>{
    Role findByName(String name);
}
