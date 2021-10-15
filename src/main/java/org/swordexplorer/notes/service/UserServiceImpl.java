package org.swordexplorer.notes.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.IllegalAddException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swordexplorer.notes.security.model.NotesUser;
import org.swordexplorer.notes.security.model.Role;
import org.swordexplorer.notes.security.repo.RoleRepo;
import org.swordexplorer.notes.security.repo.UserRepo;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    NotesUser user = userRepo.findByUsername(username);
    if (user == null) {
      log.error("User '{}' not found", username);
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    log.info("User {} was found in DB", username);

    Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .collect(Collectors.toList());
    log.info("User {} has roles: {}", username,
      user.getRoles().stream()
        .map(Role::getName)
        .collect(Collectors.toList()));

    return new User(user.getUsername(), user.getPassword(), authorities);
  }

  @Override
  public void addRoleToUser(String username, String rolename) {

    log.info("Saving new role {} fo user {}", rolename, username);
    NotesUser u = userRepo.findByUsername(username);
    if (u == null) {
      throw new IllegalAddException("No such username:" + username);
    }
    Role r = roleRepo.findByName(rolename);
    u.getRoles().add(r);
  }

  @Override
  public List<NotesUser> getUsers() {
    log.info("Getting all users.");

    return userRepo.findAll();
  }

  @Override
  public NotesUser getUser(String username) {
    log.info("Finding User {}", username);
    return userRepo.findByUsername(username);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving Role {}", role.getName());
    return roleRepo.save(role);
  }

  @Override
  public NotesUser saveUser(NotesUser user) {
    log.info("Saving User {}", user.getName());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

}
