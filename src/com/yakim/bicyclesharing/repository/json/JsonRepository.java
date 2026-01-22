package com.yakim.bicyclesharing.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yakim.bicyclesharing.exeption.RepositoryException;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.util.LocalDateTimeAdapter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class JsonRepository<T, ID> implements Repository<T, ID> {

  protected final Function<T, ID> idExtractor;
  protected Path filePath;
  protected Type listType;
  protected Gson gson;

  public JsonRepository(Function<T, ID> idExtractor, String filePath, Type listType, Gson gson) {
    this.idExtractor = idExtractor;
    this.filePath = Path.of(filePath);
    this.listType = listType;
    this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            new LocalDateTimeAdapter())
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    ensureDirectoryExists();
  }

  private void ensureDirectoryExists() {
    Path parent = filePath.getParent();
    if (parent != null && !Files.exists(parent)) {
      try {
        Files.createDirectories(parent);
      } catch (IOException e) {
        throw new RepositoryException(
            "Не вдалося створити директорію: " + parent, e
        );
      }
    }
  }

  @Override
  public T save(T entity) {
    List<T> entities = findAllInternal();
    ID id = idExtractor.apply(entity);

    boolean updated = false;
    for (int i = 0; i < entities.size(); i++) {
      if (idExtractor.apply(entities.get(i)).equals(id)) {
        entities.set(i, entity);
        updated = true;
        break;
      }

    }
    if (!updated) {
      entities.add((entity));
    }
    writeToFile(entities);
    return entity;
  }

  @Override
  public T update(T entity) {
    List<T> entities = findAllInternal();
    ID id = idExtractor.apply(entity);

    for (int i = 0; i < entities.size(); i++) {
      if (idExtractor.apply(entities.get(i)).equals(id)) {
        entities.set(i, entity);
        writeToFile(entities);
        return entity;
      }
    }

    throw new RepositoryException(
        "Сутність з id " + id + " не знайдена для оновлення"
    );
  }

  @Override
  public List<T> findAll() {
    return new ArrayList<>(findAllInternal());
  }

  @Override
  public Optional<T> findById(ID id) {
    return findAllInternal().stream()
        .filter(e -> idExtractor.apply(e).equals(id))
        .findFirst();
  }

  @Override
  public boolean deleteById(ID id) {
    List<T> entities = findAllInternal();
    boolean deleted = entities.removeIf(
        e -> idExtractor.apply(e).equals(id)
    );

    if (deleted) {
      writeToFile(entities);
    }
    return deleted;
  }


  @Override
  public boolean delete(T entity) {
    return deleteById(idExtractor.apply(entity));
  }

  @Override
  public boolean existsById(ID id) {
    return findById(id).isPresent();
  }

  @Override
  public long count() {
    return findAllInternal().size();
  }

  private List<T> findAllInternal() {
    if (!Files.exists(filePath)) {
      return new ArrayList<>();
    }

    try (Reader reader = new FileReader(filePath.toFile())) {
      List<T> data = gson.fromJson(reader, listType);
      return data != null ? new ArrayList<>(data) : new ArrayList<>();
    } catch (IOException e) {
      throw new RepositoryException(
          "Помилка читання файлу: " + filePath, e
      );
    }
  }

  private void writeToFile(List<T> entities) {
    try (Writer writer = new FileWriter(filePath.toFile())) {
      gson.toJson(entities, writer);
    } catch (IOException e) {
      throw new RepositoryException(
          "Помилка запису файлу: " + filePath, e
      );
    }
  }

  protected List<T> findBy(Predicate<T> predicate) {
    return findAllInternal().stream()
        .filter(predicate)
        .toList();
  }

}
