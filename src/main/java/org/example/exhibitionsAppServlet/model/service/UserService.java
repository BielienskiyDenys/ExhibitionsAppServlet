package org.example.exhibitionsAppServlet.model.service;

import org.example.exhibitionsAppServlet.model.dao.DaoFactory;
import org.example.exhibitionsAppServlet.model.dao.UserDao;
import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<User> getAllUsers() {
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.findAll();
        }
    }

    public Optional<User> getUserById(int id) {
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.findByID(id);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.findUserByEmail(email);
        }
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password){
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.findUserByEmailAndPassword(email, password);
        }
    }

    public List<User> getUsersByRoleId(int roleId) {
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.findUserByUserRole(roleId);
        }
    }

    public boolean addUser(UserDTO userDTO){
        User user = new User(userDTO);
        return addUser(user);
    }

    public boolean addUser(User user){
        try (UserDao dao = daoFactory.createUserDao()){
            return dao.create(user);
        }
    }

}
