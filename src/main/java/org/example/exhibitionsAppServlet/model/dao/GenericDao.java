package org.example.exhibitionsAppServlet.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    boolean create(T entity);
    Optional<T> findByID(int id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(int id);
    void close();
}
