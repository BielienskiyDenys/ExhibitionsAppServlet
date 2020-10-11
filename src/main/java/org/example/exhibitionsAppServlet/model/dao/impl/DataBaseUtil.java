package org.example.exhibitionsAppServlet.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.example.exhibitionsAppServlet.controller.commands.LoginCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseUtil {
    private static final java.util.logging.Logger logger = Logger.getLogger(DataBaseUtil.class.getName());

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error while closing AutoCloseable object.", ex);
                throw new IllegalStateException("Cannot close " + ac);
            }
        }
    }
}
