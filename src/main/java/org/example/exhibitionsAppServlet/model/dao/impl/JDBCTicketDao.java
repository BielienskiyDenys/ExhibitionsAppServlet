package org.example.exhibitionsAppServlet.model.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.example.exhibitionsAppServlet.model.dao.TicketDao;
import org.example.exhibitionsAppServlet.model.dao.mapper.ExhibitionMapper;
import org.example.exhibitionsAppServlet.model.dao.mapper.TicketMapper;
import org.example.exhibitionsAppServlet.model.dao.mapper.UserMapper;
import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.Ticket;
import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.enums.TicketStatus;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCTicketDao implements TicketDao {
    private Connection connection;
    private static final java.util.logging.Logger logger = Logger.getLogger(JDBCTicketDao.class.getName());

    public JDBCTicketDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Ticket ticket) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_TICKET);
            int k = 1;
            pstmt.setLong(k++, ticket.getUser().getId());
            pstmt.setLong(k++, ticket.getExhibition().getId());
            pstmt.setLong(k++, ticket.getTicketStatus().ordinal());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    ticket.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while creating new ticket: " + ticket, e);
            return false;
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO, "New ticket created: " + ticket);
        return true;
    }

    @Override
    public Optional<Ticket> findByID(int id) {
        Map<Long, Ticket> ticketMap = new HashMap<>();
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        TicketMapper ticketMapper = new TicketMapper();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        UserMapper userMapper = new UserMapper();

        List<Ticket> ticketList = new ArrayList<>(ticketMap.values());
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SQLConstants.FIND_TICKET_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(userMap, user);
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                ticket = ticketMapper.makeUnique(ticketMap, ticket);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching ticket by id: " + id, e);
            return Optional.empty();
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        Ticket ticket = ticketMap.get(id);
        ticket.setExhibition(exhibitionMap.get(ticket.getExhibition().getId()));
        ticket.setUser(userMap.get(ticket.getUser().getId()));

        return Optional.ofNullable(ticket);
    }

    @Override
    public List findAll() {
        Map<Long, Ticket> ticketMap = new HashMap<>();
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        TicketMapper ticketMapper = new TicketMapper();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        UserMapper userMapper = new UserMapper();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQLConstants.FIND_ALL_TICKETS);
            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(userMap, user);
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                ticket = ticketMapper.makeUnique(ticketMap, ticket);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while searching for all tickets.", e);
            return Collections.EMPTY_LIST;
        }
        List<Ticket> ticketList = new ArrayList<>(ticketMap.values());

        for (Ticket t : ticketList) {
            t.setExhibition(exhibitionMap.get(t.getExhibition().getId()));
            t.setUser(userMap.get(t.getUser().getId()));
        }
        return ticketList;
    }

    @Override
    public boolean update(Ticket ticket) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_TICKET_STATUS);
            pstmt.setInt(1, ticket.getTicketStatus().ordinal());
            pstmt.setLong(2, ticket.getId());
            if (pstmt.executeUpdate() > 0) {
                logger.log(Level.INFO, "Ticket updated:" + ticket);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating ticket: " + ticket, e);
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
            pstmt = connection.prepareStatement(SQLConstants.DELETE_TICKET_BY_ID);
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0) {
                logger.log(Level.INFO, "Ticket deleted by id:" + id);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleteing the ticket by id: ", e);
        } finally {
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO, "Failed deleting ticket by id:" + id);
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

    public List<Ticket> findByUserID(int id) {
        return findBySomeID(id, SQLConstants.FIND_TICKETS_BY_USER_ID, "Error while searching ticket by user id: ");
    }

    public List<Ticket> findByExhibitionID(int id) {
        return findBySomeID(id, SQLConstants.FIND_TICKETS_BY_EXHIBITION_ID, "Error while searching ticket by exhibition id: ");
    }

    public List<Ticket> findByTicketStatus(TicketStatus status) {
        int status_id = status.ordinal();
        return findBySomeID(status_id, SQLConstants.FIND_TICKETS_BY_TICKET_STATUS, "Error while searching ticket by exhibition id: ");
    }


    public List<Ticket> findBySomeID(int id, String sqlQuery, String errorMessage) {
        Map<Long, Ticket> ticketMap = new HashMap<>();
        Map<Long, Exhibition> exhibitionMap = new HashMap<>();
        Map<Long, User> userMap = new HashMap<>();

        TicketMapper ticketMapper = new TicketMapper();
        ExhibitionMapper exhibitionMapper = new ExhibitionMapper();
        UserMapper userMapper = new UserMapper();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Exhibition exhibition = exhibitionMapper.extractFromResultSet(rs);
                exhibition = exhibitionMapper.makeUnique(exhibitionMap, exhibition);
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(userMap, user);
                Ticket ticket = ticketMapper.extractFromResultSet(rs);
                ticket = ticketMapper.makeUnique(ticketMap, ticket);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage + id, e);
            return Collections.EMPTY_LIST;
        } finally {
            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        List<Ticket> ticketList = new ArrayList<>(ticketMap.values());

        for (Ticket t : ticketList) {
            t.setExhibition(exhibitionMap.get(t.getExhibition().getId()));
            t.setUser(userMap.get(t.getUser().getId()));
        }
        return ticketList;
    }

    @Override
    public boolean createNewTickets(Long exhibitionId, Long userId, int quantity) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_TICKET);
            int k = 1;
            pstmt.setLong(k++, userId);
            pstmt.setLong(k++, exhibitionId);
            pstmt.setInt(k++, TicketStatus.ACTIVE.ordinal());

            if (quantity == 1) {
                if (pstmt.executeUpdate() > 0) {
                    logger.log(Level.INFO, "New ticket(s) added.");
                    return true;
                }
            } else {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                for (int i = 0; i < quantity; i++) {
                    if (pstmt.executeUpdate() > 0) {
                        result = true;
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                logger.log(Level.SEVERE, "Error while transaction rollback.", exception);
            }

            logger.log(Level.SEVERE, "Error while creating new ticket.", e);
            return false;
        } finally {

            DataBaseUtil.close(rs);
            DataBaseUtil.close(pstmt);
        }
        logger.log(Level.INFO, "New ticket(s) added.");
        return true;
    }


}
