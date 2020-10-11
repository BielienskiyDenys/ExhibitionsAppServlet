package org.example.exhibitionsAppServlet.model.service;

import org.example.exhibitionsAppServlet.model.dao.DaoFactory;
import org.example.exhibitionsAppServlet.model.dao.TicketDao;
import org.example.exhibitionsAppServlet.model.entity.Ticket;
import org.example.exhibitionsAppServlet.model.entity.enums.TicketStatus;

import java.util.List;
import java.util.Optional;

public class TicketService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Ticket> getAllTickets() {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.findAll();
        }
    }

    public Optional<Ticket> getTicketById(int id) {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.findByID(id);
        }
    }

    public List<Ticket> getTicketsByUserId(int id) {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.findByUserID(id);
        }
    }

    public List<Ticket> getTicketsByExhibitionId(int id) {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.findByExhibitionID(id);
        }
    }

    public boolean createNewTicket(Long exhibitionId, Long userId, int quantity) {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.createNewTickets(exhibitionId, userId, quantity);
        }
    }

    public List<Ticket> getTicketsByStatus(TicketStatus ticketStatus) {
        try (TicketDao dao = daoFactory.createTicketDao()){
            return dao.findByTicketStatus(ticketStatus);
        }
    }
}
