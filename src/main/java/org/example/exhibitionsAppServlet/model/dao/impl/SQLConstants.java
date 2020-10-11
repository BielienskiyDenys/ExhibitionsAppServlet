package org.example.exhibitionsAppServlet.model.dao.impl;

public class SQLConstants {

    public final static String FIND_EXHIBITION_BY_ID = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id=exhibition_has_hall.exhibition_id WHERE exhibition.id = ?;";
    public final static String FIND_EXHIBITION_BY_NAME_LIKE = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ex_name LIKE ?;";
    public final static String FIND_EXHIBITION_BY_THEMES_LIKE = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE themes LIKE ?;";
    public final static String FIND_EXHIBITION_BY_DATES_BETWEEN = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE start_date BETWEEN '?' AND '?' OR end_date BETWEEN '?' AND '?' OR start_date <='?' AND end_date >='?';";
    public final static String FIND_EXHIBITION_BY_STATUS_ID = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE exhibition_status_id = ?;";
    public final static String FIND_ALL_EXHIBITIONS = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id=exhibition_has_hall.exhibition_id;";
    public final static String CREATE_NEW_EXHIBITION = "INSERT INTO exhibition(ex_name, start_date, end_date, open_time, close_time, description, price, themes, exhibition_status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public final static String CREATE_NEW_EXHIBITION_HAS_HALL = "INSERT INTO exhibition_has_hall(exhibition_id, hall_id) VALUES (?, ?);";
    public final static String UPDATE_EXHIBITION_BY_ID = "UPDATE exhibition SET ex_name=?, start_date=?, end_date=?, opne_time=?, close_time=?, description=?, price=?, themes=?, exhibition_status_id=? WHERE id=?;";
    public final static String DELETE_EXHIBITION_BY_ID = " DELETE FROM exhibition WHERE id=?;";
    public final static String FIND_EXHIBITION_IF_TIME_IS_TAKEN = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id=exhibition_has_hall.exhibition_id \n" +
            "WHERE exhibition_has_hall.hall_id = ? \n" +
            "AND (\n" +
            "exhibition.start_date <= ? AND exhibition.end_date >= ?\n" +
            "OR\n" +
            "exhibition.start_date BETWEEN ? AND ?\n" +
            "OR\n" +
            "exhibition.end_date BETWEEN ? AND ?\n" +
            ");";

    public final static String FIND_EXHIBITION_IF_TIME_IS_TAKEN_FOR_ADDING_HALL = "SELECT * FROM exhibition LEFT JOIN exhibition_has_hall ON exhibition.id=exhibition_has_hall.exhibition_id \n" +
            "WHERE exhibition_has_hall.hall_id = ? \n" +
            "AND (\n" +
                "exhibition.start_date <= (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND exhibition.end_date >= (SELECT end_date FROM exhibition WHERE exhibition.id = ?)\n" +
                "OR\n" +
                "exhibition.start_date BETWEEN (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND (SELECT end_date FROM exhibition WHERE exhibition.id = ?)\n" +
                "OR\n" +
                "exhibition.end_date BETWEEN (SELECT start_date FROM exhibition WHERE exhibition.id = ?) AND (SELECT end_date FROM exhibition WHERE exhibition.id = ?));";

    public final static String MANUAL_UPDATE_EXPIRED_EXHIBITIONS = "UPDATE exhibition SET exhibition_status_id=2 WHERE (end_date<CURDATE() AND exhibition_status_id=1);";
    public final static String MANUAL_UPDATE_EXPIRED_TICKETS = "UPDATE ticket SET ticket_status_id=2 WHERE exhibition_id = (SELECT exhibition.id FROM exhibition WHERE exhibition.exhibition_status_id = 2) AND ticket_status_id = 1;";


    public final static String CREATE_NEW_USER = "INSERT INTO usr(email, password, username, user_role_id) VALUES (?, ?, ?, ?);";
    public final static String FIND_ALL_USERS = "SELECT * FROM usr";
    public final static String FIND_USER_BY_ID = "SELECT * FROM usr WHERE id=?;";
    public final static String FIND_USER_BY_USERNAME ="SELECT * FROM usr WHERE username=?;";
    public final static String FIND_USER_BY_EMAIL ="SELECT * FROM usr WHERE email=?;";
    public final static String FIND_USER_BY_EMAIL_AND_PASSWORD ="SELECT * FROM usr WHERE email=? AND password=?;";
    public final static String FIND_USER_BY_USER_ROLE_ID = "SELECT * FROM usr WHERE user_role_id=?;";
    public final static String UPDATE_USER_BY_ID = "UPDATE usr SET email=?, password=?, username=?, user_role_id=? WHERE id=?;";
    public final static String DELETE_USER_BY_ID = "DELETE FROM usr WHERE id=1;";



    public final static String CREATE_NEW_TICKET = "INSERT INTO ticket(usr_id, exhibition_id, ticket_status_id) VALUES (?, ?, ?);";
    public final static String FIND_ALL_TICKETS = "SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id;";
    public final static String FIND_TICKET_BY_ID = "SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.id=?;";
    public final static String FIND_TICKETS_BY_USER_ID = "SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.usr_id=?";
    public final static String FIND_TICKETS_BY_EXHIBITION_ID = "SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.exhibition_id=?;";
    public final static String FIND_TICKETS_BY_TICKET_STATUS = "SELECT * FROM ticket LEFT JOIN usr ON ticket.usr_id = usr.id LEFT JOIN exhibition ON ticket.exhibition_id = exhibition.id LEFT JOIN exhibition_has_hall ON exhibition.id = exhibition_has_hall.exhibition_id WHERE ticket.ticket_status_id=?;";
    public final static String UPDATE_TICKET_STATUS = "UPDATE ticket SET ticket_status_id=? WHERE ticket.id=?;";
    public final static String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE id=?;";

}
