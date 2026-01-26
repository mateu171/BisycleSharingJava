package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.domain.security.PasswordHasher;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.repository.UserRepository;
import java.util.List;
import java.util.UUID;

public class UserService extends BaseService<User, UUID> {

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
  }

  public boolean existsByLogin(String login) {
    return userRepository.findByLogin(login) != null;
  }

  @Override
  protected Repository<User, UUID> getRepository() {
    return userRepository;
  }

  @Override
  public User add(User entity) {
    entity.setPassword(PasswordHasher.hash(entity.getPassword()));
    return getRepository().save(entity);
  }

  public User getByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public List<User> getByRole(Role role) {
    return userRepository.findByRole(role);
  }

}
