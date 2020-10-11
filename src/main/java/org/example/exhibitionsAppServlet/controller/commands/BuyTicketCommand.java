package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.service.TicketService;

import javax.servlet.http.HttpServletRequest;

public class BuyTicketCommand implements Command{
    //todo logger
    private TicketService ticketService;

    public BuyTicketCommand(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Long exhibitionId = Long.parseLong(request.getParameter("exhibition_id"));
        Long userId = Long.parseLong(request.getParameter("user_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if(ticketService.createNewTicket(exhibitionId, userId, quantity)){
            //TODO log
            request.setAttribute("buy_ticket_message", "Ticket(s) bought.");
            return "/index.jsp";
        } else {
            //TODO log
            request.setAttribute("buy_ticket_message", "Failed to buy ticket.");
            return "/index.jsp";
        }

    }
}
