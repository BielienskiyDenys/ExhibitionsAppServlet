package org.example.exhibitionsAppServlet.controller.commands;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}
