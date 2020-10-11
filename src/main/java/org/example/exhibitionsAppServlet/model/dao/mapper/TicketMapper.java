package org.example.exhibitionsAppServlet.model.dao.mapper;

import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.entity.Ticket;
import org.example.exhibitionsAppServlet.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TicketMapper implements ObjectMapper<Ticket>{
    @Override
    public Ticket extractFromResultSet(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("ticket.id"));
        Exhibition exhibitionForTicket = new ExhibitionMapper().extractFromResultSet(rs);
        ticket.setExhibition(exhibitionForTicket);
        User userForTicket = new UserMapper().extractFromResultSet(rs);
        ticket.setUser(userForTicket);
        ticket.setTicketStatus(rs.getInt("ticket.ticket_status_id"));
        return ticket;
    }


    @Override
    public Ticket makeUnique(Map<Long, Ticket> cache, Ticket ticket) {
        cache.put(ticket.getId(), ticket);
        return cache.get(ticket.getId());
    }
}
