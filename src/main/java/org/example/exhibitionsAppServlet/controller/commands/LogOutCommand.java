package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        HttpSession session = request.getSession();
        CommandUtil.logUserOut(request, (String) session.getAttribute("userEmail"));
        CommandUtil.setUserInfoForSessionAndContext(request, UserRole.GUEST, new User());
        return "/index.jsp";
    }
}
