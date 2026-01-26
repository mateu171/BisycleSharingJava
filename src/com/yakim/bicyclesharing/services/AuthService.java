package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.security.PasswordHasher;
import com.yakim.bicyclesharing.exeption.AuthException;
import com.yakim.bicyclesharing.repository.UserRepository;

public class AuthService {

  private final UserRepository userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User authenticate(String login, String password) {
    User user = userRepository.findByLogin(login);

    if (user == null || !PasswordHasher.verify(password, user.getPassword())) {
      throw new AuthException("Невірний логін або пароль");
    }

    return user;
  }
}
