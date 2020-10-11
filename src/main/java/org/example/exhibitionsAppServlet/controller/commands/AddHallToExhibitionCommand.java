package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.enums.HallName;
import org.example.exhibitionsAppServlet.model.service.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class AddHallToExhibitionCommand implements Command{
    //TODO log
    private ExhibitionService exhibitionService;

    public AddHallToExhibitionCommand(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
       Long exhibitionId = Long.parseLong(request.getParameter("exhibition_id"));
       HallName hallToAdd = HallName.valueOf(request.getParameter("hall_name"));
       if (exhibitionService.addHallToExhibition(exhibitionId, hallToAdd)) {
           //TODO log
           request.setAttribute("add_hall_to_exhibition_message", "Hall added.");
           return "/admin_exhibition_management.jsp";
       } else {
           //TODO log
           request.setAttribute("add_hall_to_exhibition_message", "Failed to add hall. It is occupied for theese dates.");
           return "/admin_exhibition_management.jsp";
       }
    }
}
