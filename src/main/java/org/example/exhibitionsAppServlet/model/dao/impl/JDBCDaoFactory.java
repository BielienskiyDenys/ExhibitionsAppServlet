package org.example.exhibitionsAppServlet.model.dao.impl;

import org.example.exhibitionsAppServlet.model.dao.DaoFactory;
import org.example.exhibitionsAppServlet.model.dao.ExhibitionDao;
import org.example.exhibitionsAppServlet.model.dao.TicketDao;
import org.example.exhibitionsAppServlet.model.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public ExhibitionDao createExhibitionDao() {
        return new JDBCExhibitionDao(getConnection());
    }

    @Override
    public TicketDao createTicketDao() {
        return new JDBCTicketDao(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    //TODO исключение не обработано!
    private Connection getConnection() {
        try{
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
