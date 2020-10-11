package org.example.exhibitionsAppServlet.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.example.exhibitionsAppServlet.model.dao.UserDao;
import org.example.exhibitionsAppServlet.model.dao.mapper.UserMapper;
import org.example.exhibitionsAppServlet.model.entity.User;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private static final java.util.logging.Logger logger = Logger.getLogger(JDBCUserDao.class.getName());
    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(User user) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getUsername());
            pstmt.setInt(k++, user.getUserRole().ordinal());


            if(pstmt.executeUpdate()>0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            } else {
                logger.log(Level.SEVERE, "Error during creation new user: " + user);
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during creation new user: " + user, e);
            return false;
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO, "Created new user: " + user);
        return true;
    }

    @Override
    public Optional<User> findByID(int id) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserMapper().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching user by id: "+ id, e);
            return Optional.empty();
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        return Optional.ofNullable(user);

    }

    @Override
    public List findAll() {
        Map<Long, User> userMap = new HashMap<>();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQLConstants.FIND_ALL_USERS);

            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(userMap, user);
            }
            return new ArrayList(userMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error while searching for all users.", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean update(User user) {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_USER_BY_ID);
            int k = 1;
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getUsername());
            pstmt.setInt(k++, user.getUserRole().ordinal());
            pstmt.setLong(k++, user.getId());

            if(pstmt.executeUpdate()>0) {
                logger.log(Level.INFO,"User updated: " + user);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Error during updating a user: " + user, e);
            return false;
        } finally {
            DataBaseUtil.close(pstmt);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.DELETE_USER_BY_ID);
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0) {
                logger.log(Level.INFO, "User deleted by id:" + id);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleteing the user by id: ", e);
        } finally {
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO,"Failed deleting user by id:" + id);
        return false;
    }

    @Override
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<User> findUserByEmail(String email) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserMapper().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching user by email: " + email, e);
            return Optional.empty();
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        return Optional.ofNullable(user);

    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_EMAIL_AND_PASSWORD);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserMapper().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching user by email: "+ email+ " And password: " + password, e);
            return Optional.empty();
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        return Optional.ofNullable(user);

    }

    @Override
    public List<User> findUserByUserRole(int roleId) {
        Map<Long, User> userMap = new HashMap<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_USER_ROLE_ID);
            pstmt.setInt(1, roleId);
            rs = pstmt.executeQuery();

            UserMapper userMapper = new UserMapper();
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(userMap, user);
            }
            return new ArrayList(userMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching for users by role: " + roleId, e);
            return Collections.EMPTY_LIST;
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
    }
}
