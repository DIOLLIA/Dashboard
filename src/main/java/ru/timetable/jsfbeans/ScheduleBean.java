package ru.timetable.jsfbeans;


import lombok.Getter;
import lombok.Setter;
import ru.timetable.jms.NotifyConsumer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.Date;

/**
 * Bean for managing schedule by rest service and connecting to message broker
 */
@ManagedBean
@ViewScoped
@Getter
@Setter
public class ScheduleBean implements Serializable {

    private String stationName;
    private Date date;

    @EJB
    private NotifyConsumer receiver;

    public void checkQueue() throws Exception {


        try {
            receiver.createConnection();
//            requestSchedule();
            receiver.receive();
        } catch (JMSException e) {
            System.out.println("JMS Exception!");
        } catch (Exception e) {
            System.out.println("");
        } finally {
            receiver.closeConnection();
        }

    }
}

