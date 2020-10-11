package org.example.exhibitionsAppServlet.controller.commands;


import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.service.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllExhibitionsCommand implements Command{
    //TODO logger
    private ExhibitionService exhibitionService;

    public ShowAllExhibitionsCommand(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Exhibition> exhibitionList = exhibitionService.getAllExhibitions();
        request.setAttribute("exhibitionList", exhibitionList);
        if (exhibitionList.isEmpty()) {
            request.setAttribute("exhibition_search_message", "No exhibitions found.");
        }
        return "/index.jsp";
    }
}
