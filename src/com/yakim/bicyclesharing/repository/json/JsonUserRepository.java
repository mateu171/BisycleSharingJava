package com.yakim.bicyclesharing.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonUserRepository extends JsonRepository<User, UUID> implements UserRepository {

  public JsonUserRepository(String filename) {
    super(
        User::getId,
        filename,
        new TypeToken<ArrayList<User>>() {
        }.getType(),
        new GsonBuilder().setPrettyPrinting().serializeNulls().create()
    );
  }

  @Override
  public List<User> findByRole(Role role) {
    return findBy(r -> r.getRole() == role);
  }

  @Override
  public User findByLogin(String login) {
    return findAll().stream().filter(l -> l.getLogin().equalsIgnoreCase(login)).findFirst()
        .orElse(null);
  }
}
