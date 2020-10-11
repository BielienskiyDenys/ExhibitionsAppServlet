package org.example.exhibitionsAppServlet.model.dao;

import org.example.exhibitionsAppServlet.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    List<User> findUserByUserRole(int id);
}
