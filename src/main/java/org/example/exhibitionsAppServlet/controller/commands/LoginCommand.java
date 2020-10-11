package org.example.exhibitionsAppServlet.controller.commands;


import org.example.exhibitionsAppServlet.model.entity.User;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;
import org.example.exhibitionsAppServlet.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCommand implements Command{
    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());
        private UserService userService;

    public LoginCommand(UserService userService){this.userService = userService;}

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if( email==null || email.equals("") || pass==null || pass.equals("")) {
            System.out.println("Empty email or pass");
            //TODO Дать понять юзеру, что введены невалидные данные
            return "/login.jsp";
        }

           Optional<User> optionalUser = userService.getUserByEmailAndPassword(email, pass);
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(pass)) {
            //TODO password encoding
            if(CommandUtil.checkUserIsLogged(request, email)){
                return "/WEB-INF/error.jsp";
            };

//            request.getSession().setAttribute("currentUser" , optionalUser.get());

            if(optionalUser.get().getUserRole().name().equals("ADMIN")) {
                CommandUtil.setUserInfoForSessionAndContext(request, UserRole.ADMIN, optionalUser.get());
            } else if (optionalUser.get().getUserRole().name().equals("USER")){
                CommandUtil.setUserInfoForSessionAndContext(request, UserRole.USER, optionalUser.get());
            }

            logger.log(Level.INFO, "User logged in. {}", optionalUser);
            return "/index.jsp";
        }
        logger.log(Level.INFO, "Invalid attemp of login: {}", email);
        return "/login.jsp";
    }
}
