package org.example.exhibitionsAppServlet.model.dao;

import org.example.exhibitionsAppServlet.model.entity.Ticket;
import org.example.exhibitionsAppServlet.model.entity.enums.TicketStatus;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> findByUserID(int id);
    List<Ticket> findByExhibitionID(int id);
    List<Ticket> findByTicketStatus(TicketStatus status);
    boolean createNewTickets(Long exhibitionId, Long userId, int quantity);
}
