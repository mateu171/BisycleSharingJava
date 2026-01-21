package com.yakim.bicyclesharing.repository;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

  List<User> findByRole(Role role);

  User findByLogin(String login);
}
