package org.example.exhibitionsAppServlet.model.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.example.exhibitionsAppServlet.controller.commands.LoginCommand;
import org.example.exhibitionsAppServlet.model.dao.ExhibitionDao;
import org.example.exhibitionsAppServlet.model.dao.mapper.ExhibitionMapper;
import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCExhibitionDao implements ExhibitionDao {
    private Connection connection;
    private static final java.util.logging.Logger logger = Logger.getLogger(JDBCExhibitionDao.class.getName());

    public JDBCExhibitionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Exhibition exhibition) {
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_EXHIBITION, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, exhibition.getExName());
            pstmt.setDate(k++, new Date(exhibition.getStartDate().getTimeInMillis()));
            pstmt.setDate(k++, new Date(exhibition.getEndDate().getTimeInMillis()));
            pstmt.setString(k++, exhibition.getOpenTime());
            pstmt.setString(k++, exhibition.getCloseTime());
            pstmt.setString(k++, exhibition.getDescription());
            pstmt.setLong(k++, exhibition.getPrice());
            pstmt.setString(k++, exhibition.getThemes());
            pstmt.setInt(k++, exhibition.getExhibitionStatus().ordinal());

            pstmt2 = connection.prepareStatement(SQLConstants.CREATE_NEW_EXHIBITION_HAS_HALL);


            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            if (!checkIfExhibitionCanBeAdded(exhibition)) {
                connection.setAutoCommit(true);
                return false;
            }
            if (pstmt.executeUpdate()>0) {
            rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    exhibition.setId(rs.getLong(1));
                }
            } else {
                logger.log(Level.SEVERE,"Error while creating new exposition: {}", exhibition);
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            pstmt2.setLong(1, exhibition.getId());
            pstmt2.setInt(2, exhibition.getHallNameList().get(0).ordinal());
            pstmt2.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                logger.log(Level.SEVERE, "Error while creating new exposition: "+exhibition, e);
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            } catch (SQLException exception) {
                logger.log(Level.SEVERE, "Error while rollback during creation of new exposition.", exception);
                return false;
            }
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
            DataBaseUtil.close(pstmt2);
        }
        logger.log(Level.INFO, "New exhibition created: {}", exhibition);
        return true;

    }

    @Override
    public Optional<Exhibition> findByID(int id) {
        Exhibition exhibition = null;
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        List<Exhibition> exhibitionList = new ArrayList<>();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_EXHIBITION_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Exhibition exhibitionNew = exhibitionMapper.extractFromResultSet(rs);
                exhibitionNew = exhibitionMapper.makeUnique(exhibitionMap, exhibitionNew);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching an exposition: " + exhibition, e);
            return Optional.empty();

        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        exhibition = exhibitionMap.get(id);
        return Optional.ofNullable(exhibition);

    }

    @Override
    public List findAll() {
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        ResultSet rs = null;
        try (Statement st = connection.createStatement()) {
             rs = st.executeQuery(SQLConstants.FIND_ALL_EXHIBITIONS);

            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
            }
            return new ArrayList<>(exhibitionMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching for all exhibitions list.", e);
            return Collections.EMPTY_LIST;
        }finally {
            DataBaseUtil.close(rs);
        }

    }

    @Override
    public boolean update(Exhibition exhibition) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_EXHIBITION_BY_ID);
            int k = 1;
            pstmt.setString(k++, exhibition.getExName());
            pstmt.setDate(k++, new Date(exhibition.getStartDate().getTimeInMillis()));
            pstmt.setDate(k++, new Date(exhibition.getEndDate().getTimeInMillis()));
            pstmt.setString(k++, exhibition.getOpenTime());
            pstmt.setString(k++, exhibition.getCloseTime());
            pstmt.setString(k++, exhibition.getDescription());
            pstmt.setLong(k++, exhibition.getPrice());
            pstmt.setString(k++, exhibition.getThemes());
            pstmt.setInt(k++, exhibition.getExhibitionStatus().ordinal());
            pstmt.setLong(k++, exhibition.getId());
            if (pstmt.executeUpdate() > 0) {
                logger.log(Level.INFO, "Exhibition updated: {}", exhibition);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating an exposition: " + exhibition, e);
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
            pstmt = connection.prepareStatement(SQLConstants.DELETE_EXHIBITION_BY_ID);
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0) {
                logger.log(Level.INFO, "Exhibition deleted by id: {}", id);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleteing the exposition by id: ", e);
        } finally {
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO, "Failed deleting exhibition by id: {}", id);
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfExhibitionCanBeAdded(Exhibition exhibition) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_EXHIBITION_IF_TIME_IS_TAKEN);
            pstmt.setInt(1, exhibition.getHallNameList().get(0).ordinal());
            Date myStartDate = new Date(exhibition.getStartDate().getTimeInMillis());
            Date myEndDate = new Date(exhibition.getEndDate().getTimeInMillis());
            pstmt.setDate(2, myStartDate);
            pstmt.setDate(4, myStartDate);
            pstmt.setDate(6, myStartDate);
            pstmt.setDate(3, myEndDate);
            pstmt.setDate(5, myEndDate);
            pstmt.setDate(7, myEndDate);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                logger.log(Level.INFO, "Attempt to create an exhibition on non-available dates: {}", exhibition);
                return false;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while checking dates availability. ", e);
            return false;

        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        return true;
    }

    @Override
    public List findByNameLike(String exNameLike) {
        return findByColumnLike(exNameLike, SQLConstants.FIND_EXHIBITION_BY_NAME_LIKE, "Error while searching for exhibition list by name like: ");
    }

    @Override
    public List<Exhibition> findByThemeLike(String themeLike) {
        return findByColumnLike(themeLike, SQLConstants.FIND_EXHIBITION_BY_THEMES_LIKE, "Error while searching for exhibition list by theme like: ");
    }


    public List<Exhibition> findByColumnLike(String searchRequest, String sqlQuery, String errorMessage) {
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        ResultSet rs = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            //TODO экранирование like?
            pstmt.setString(1, searchRequest);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
            }
            return new ArrayList<>(exhibitionMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage + searchRequest, e);
            return new ArrayList<>();
        } finally {
            DataBaseUtil.close(rs);
        }
    }

    @Override
    public List<Exhibition> findByDatesBetween(String searchDateFrom, String searchDateTo) {
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        ResultSet rs = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_EXHIBITION_BY_DATES_BETWEEN)) {
            int k = 1;
            pstmt.setString(k++, searchDateFrom);
            pstmt.setString(k++, searchDateTo);
            pstmt.setString(k++, searchDateFrom);
            pstmt.setString(k++, searchDateTo);
            pstmt.setString(k++, searchDateFrom);
            pstmt.setString(k++, searchDateTo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
            }
            return new ArrayList<>(exhibitionMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching for exhibition list between dates: " + searchDateFrom + " and " + searchDateTo, e);
            return new ArrayList<>();
        } finally {
            DataBaseUtil.close(rs);
        }
    }

    @Override
    public List<Exhibition> findByStatusId(int id) {
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        ResultSet rs = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_EXHIBITION_BY_STATUS_ID)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
            }
            return new ArrayList<>(exhibitionMap.values());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching for exhibition list by status_id: " + id, e);
            return new ArrayList<>();
        }
    }

    public boolean addHallToExhibition(Long exhibitionId, HallName hallToAdd) {
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        boolean result = false;

        try {
             pstmt = connection.prepareStatement(SQLConstants.FIND_EXHIBITION_IF_TIME_IS_TAKEN_FOR_ADDING_HALL);
            pstmt2 = connection.prepareStatement(SQLConstants.CREATE_NEW_EXHIBITION_HAS_HALL);
            int k = 1;
            pstmt.setInt(k++, hallToAdd.ordinal());
            for (int i = 0; i<6; i++) {
            pstmt.setLong(k++, exhibitionId);
            }

            pstmt2.setLong(1, exhibitionId);
            pstmt2.setInt(2, hallToAdd.ordinal());

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                logger.log(Level.INFO, "Attempt to add hal for an exhibition on non-available dates: " + exhibitionId + hallToAdd);
                connection.commit();
                connection.setAutoCommit(true);
                return false;
            }
            if(pstmt2.executeUpdate()>0){
                logger.log(Level.INFO, "New hall added for exhibition: " + exhibitionId + hallToAdd);
                result = true;
            }
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during adding hall to exhibition.", e);
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.SEVERE, "Error during transaction rollback.", exception);
            }
            return false;
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
            DataBaseUtil.close(pstmt2);
        }
    }

    @Override
    public boolean updateExpiredExhibitionsAndTicketsManually() {
        try (Statement st = connection.createStatement()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            st.executeUpdate(SQLConstants.MANUAL_UPDATE_EXPIRED_EXHIBITIONS);
            st.executeUpdate(SQLConstants.MANUAL_UPDATE_EXPIRED_TICKETS);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating exhibition and ticket status.", e);
            return false;
        }
        return true;
    }
}
