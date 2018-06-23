package ru.timetable;

import ru.timetable.jms.MessageSender;
import ru.timetable.jms.MessageSenderImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MessageSender messageSender = new MessageSenderImpl();
        try {
            messageSender.startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
