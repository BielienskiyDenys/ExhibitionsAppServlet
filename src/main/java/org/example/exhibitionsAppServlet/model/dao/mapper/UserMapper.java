package org.example.exhibitionsAppServlet.model.dao.mapper;

import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User>{
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("usr.id"));
        user.setEmail(rs.getString("usr.email"));
        user.setPassword(rs.getString("usr.password"));
        user.setUsername(rs.getString("usr.username"));
        user.setUserRole(rs.getInt("usr.user_role_id"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
