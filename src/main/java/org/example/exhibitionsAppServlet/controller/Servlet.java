package org.example.exhibitionsAppServlet.controller;

import org.example.exhibitionsAppServlet.controller.commands.*;
import org.example.exhibitionsAppServlet.model.service.ExhibitionService;
import org.example.exhibitionsAppServlet.model.service.TicketService;
import org.example.exhibitionsAppServlet.model.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private UserService userService = new UserService();
    private ExhibitionService exhibitionService = new ExhibitionService();
    private Map<String, Command> commands = new HashMap<>();



    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("login", new LoginCommand(new UserService()));
        commands.put("logout", new LogOutCommand());
        commands.put("show_all_exhibitions", new ShowAllExhibitionsCommand(new ExhibitionService()));
        commands.put("create_new_exhibition_admin", new CreateNewExhibitionCommand(new ExhibitionService()));
        commands.put("registration", new RegistrationCommand(new UserService()));
        commands.put("add_hall_to_exhibition_admin", new AddHallToExhibitionCommand(new ExhibitionService()));
        commands.put("buy_ticket", new BuyTicketCommand(new TicketService()));
        commands.put("show_my_tickets", new ShowTicketsByStatus(new TicketService()));
        commands.put("manual_expired_update_admin", new UpdateExpiredManuallyCommand(new ExhibitionService()));
        commands.put("show_exhibitions_by_param", new ShowExhibitionsByParamCommand(new ExhibitionService()));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       processRequest(request, response);
//        response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/api/" , "");
        Command command = commands.getOrDefault(path , (r)->"/index.jsp)");
        //
        System.out.println(command.getClass().getName());
        //
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/api"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
//        response.getWriter().print("Hello from servlet");
    }
    }
}
