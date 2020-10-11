package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtil {
    static void setUserInfoForSessionAndContext(HttpServletRequest request,
                            UserRole role, User currentUser) {
        HttpSession session = request.getSession();
//        ServletContext context = request.getServletContext();
//        context.setAttribute("userEmail", email);
//        context.setAttribute("userName", username);
        session.setAttribute("currentUser", currentUser);
        session.setAttribute("userRole", role.name());
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userEmail){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userEmail::equals)){
            return true;
        }
        loggedUsers.add(userEmail);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    static boolean logUserOut(HttpServletRequest request, String userEmail) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if(loggedUsers.stream().anyMatch(userEmail::equals)){
           loggedUsers.remove(userEmail);
            request.getSession().getServletContext()
                    .setAttribute("loggedUsers", loggedUsers);
            return true;
        }
        return false;
    }
}
