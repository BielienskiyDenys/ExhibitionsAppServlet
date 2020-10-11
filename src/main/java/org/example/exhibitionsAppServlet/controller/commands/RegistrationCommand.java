package org.example.exhibitionsAppServlet.controller.commands;

import org.example.exhibitionsAppServlet.model.entity.dto.UserDTO;
import org.example.exhibitionsAppServlet.model.entity.enums.UserRole;
import org.example.exhibitionsAppServlet.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command{
    //TODO logger
    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(request.getParameter("email"));
        //TODO проверка email регуляркой
        userDTO.setPassword(request.getParameter("pass"));
        userDTO.setUsername(request.getParameter("username"));
        userDTO.setUserRole(UserRole.valueOf(request.getParameter("role")));
        if (userService.addUser(userDTO)) {
            //TODO log
            return "/login.jsp";
        } else {
            //TODO log
            request.setAttribute("registration_message", "This email is registered already.");
            return "/login.jsp";
        }
    }
}
