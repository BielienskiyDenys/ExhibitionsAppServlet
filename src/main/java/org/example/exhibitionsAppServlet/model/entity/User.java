package org.example.exhibitionsAppServlet.model.entity;

import org.example.exhibitionsAppServlet.model.entity.dto.UserDTO;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;

import java.util.Objects;

public class User {
    private Long id;
    private String email;
    private String password;
    private String username;
    private UserRole userRole;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.username = userDTO.getUsername();
        this.userRole = userDTO.getUserRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setUserRole(int role_id) {
        this.userRole = UserRole.values()[role_id];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
