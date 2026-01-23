package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.repository.UserRepository;
import com.yakim.bicyclesharing.repository.json.JsonUserRepository;
import java.util.List;
import java.util.UUID;

public class UserService {

  private final UserRepository userRepository = new JsonUserRepository("data/users.json");
  
  public User addNewUser(User newUser) {
    return userRepository.save(newUser);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public List<User> getByRole(Role role) {
    return userRepository.findByRole(role);
  }

  public boolean delete(UUID id) {
    return userRepository.deleteById(id);
  }

  public boolean delete(User user) {
    return userRepository.delete(user);
  }

  public User updateUser(User updatedUser) {
    return userRepository.update(updatedUser);
  }

}
