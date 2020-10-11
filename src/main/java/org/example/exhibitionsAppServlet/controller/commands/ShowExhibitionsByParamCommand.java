package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.Exhibition;
import org.example.exhibitionsAppServlet.model.service.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowExhibitionsByParamCommand implements Command{
    //todo log
    private ExhibitionService exhibitionService;

    public ShowExhibitionsByParamCommand(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String criterion = request.getParameter("criterion");
        StringBuilder searchValue = new StringBuilder();
        searchValue.append("%").append(request.getParameter("search_value")).append("%");
        List<Exhibition> exhibitionList = new ArrayList<>();
        if (criterion.equals("name")){
            exhibitionList=exhibitionService.getExhibitionsByName(searchValue.toString());
        }else if (criterion.equals("theme")){
            exhibitionList=exhibitionService.getExhibitionsByTheme(searchValue.toString());
        }
        request.setAttribute("exhibitionList", exhibitionList);
        if (exhibitionList.isEmpty()) {
            request.setAttribute("exhibition_search_message", "No exhibitions found.");
        }
        return "/index.jsp";
    }
}
