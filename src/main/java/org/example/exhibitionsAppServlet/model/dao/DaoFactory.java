package org.example.exhibitionsAppServlet.model.dao;

import org.example.exhibitionsAppServlet.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract ExhibitionDao createExhibitionDao();
    public abstract TicketDao createTicketDao();
    public abstract UserDao createUserDao();

    public static DaoFactory getInstance(){
        if(daoFactory==null){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory=temp;
                }
            }
        }
        return daoFactory;
    }
}
