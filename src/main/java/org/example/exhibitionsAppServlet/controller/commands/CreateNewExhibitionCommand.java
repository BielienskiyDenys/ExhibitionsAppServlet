package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.dto.ExhibitionDTO;
import org.example.exhibitionsAppServlet.model.entity.enums.HallName;
import org.example.exhibitionsAppServlet.model.service.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class CreateNewExhibitionCommand implements Command {
    //TODO log
    private ExhibitionService exhibitionService;

    public CreateNewExhibitionCommand(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        ExhibitionDTO exhibitionDTO = new ExhibitionDTO();
        exhibitionDTO.setExName(request.getParameter("ex_name"));
        exhibitionDTO.setStartDate(request.getParameter("start_date"));
        exhibitionDTO.setEndDate(request.getParameter("end_date"));
        exhibitionDTO.setOpenTime(request.getParameter("open_time"));
        exhibitionDTO.setCloseTime(request.getParameter("close_time"));
        exhibitionDTO.setDescription(request.getParameter("description"));
        exhibitionDTO.setPrice(Long.parseLong(request.getParameter("price")));
        exhibitionDTO.setThemes(request.getParameter("themes"));
        exhibitionDTO.setHallName(HallName.valueOf(request.getParameter("hall_name")));
        if (exhibitionService.createNewExhibition(exhibitionDTO)) {
            //TODO log
            request.setAttribute("creating_exhibition_message", "Exhibition created with id: "+ exhibitionDTO.getId());
            return "/admin_exhibition_management.jsp";
        } else {
            //TODO log
            request.setAttribute("creating_exhibition_message", "Failed to add exhibition! Dates are occupied.");
            return "/admin_exhibition_management.jsp";
        }
    }
}
