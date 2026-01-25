package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.repository.UserRepository;
import com.yakim.bicyclesharing.repository.json.JsonUserRepository;
import java.util.List;
import java.util.UUID;

public class UserService extends BaseService<User, UUID> {

  private static final String USERS_FILE = "data/users.json";
  private final UserRepository userRepository = new JsonUserRepository(USERS_FILE);

  public boolean existsByLogin(String login) {
    return userRepository.findByLogin(login) != null;
  }

  @Override
  protected Repository<User, UUID> getRepository() {
    return userRepository;
  }

  public User getByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public List<User> getByRole(Role role) {
    return userRepository.findByRole(role);
  }

}
