package ru.timetable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("x");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
