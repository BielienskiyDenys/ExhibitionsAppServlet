package org.example.exhibitionsAppServlet.model.entity;

import org.example.exhibitionsAppServlet.model.entity.enums.TicketStatus;

public class Ticket {
    private Long id;
    private User user;
    private Exhibition exhibition;
    private TicketStatus ticketStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setTicketStatus(int statusId) {
        this.ticketStatus = TicketStatus.values()[statusId];
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", exhibition=" + exhibition.getId() +
                ", ticketStatus=" + ticketStatus +
                '}';
    }
}
