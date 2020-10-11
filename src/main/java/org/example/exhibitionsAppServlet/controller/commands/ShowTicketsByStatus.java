package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.Ticket;
import org.example.exhibitionsAppServlet.model.entity.enums.TicketStatus;
import org.example.exhibitionsAppServlet.model.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowTicketsByStatus implements Command{
    //TODO log
    private TicketService ticketService;

    public ShowTicketsByStatus(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        TicketStatus ticketStatus = TicketStatus.valueOf(request.getParameter("ticket_status"));
        List<Ticket> ticketList = ticketService.getTicketsByStatus(ticketStatus);
        request.setAttribute("ticketList", ticketList);
        if (ticketList.isEmpty()) {
            request.setAttribute("show_my_tickets_message", "No tickets found.");
        }
        return "/my_tickets.jsp";
    }
}
