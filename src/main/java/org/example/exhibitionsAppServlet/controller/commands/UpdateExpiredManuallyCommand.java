package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.service.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class UpdateExpiredManuallyCommand implements Command{
    //todo log
    private ExhibitionService exhibitionService;

    public UpdateExpiredManuallyCommand(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (exhibitionService.updateExpiredExhibitionsAndTicketsManually()){
            request.setAttribute("exhibition_management_message", "Exhibitions and tickets updated.");
        } else {
            request.setAttribute("exhibition_management_message", "Failed to update.");
        }
        return "/admin_exhibition_management.jsp";
    }
}
