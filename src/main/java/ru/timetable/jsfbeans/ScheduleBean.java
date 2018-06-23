package ru.timetable.jsfbeans;


import lombok.Getter;
import lombok.Setter;
import ru.timetable.data.SchceduleProducer;
import ru.timetable.jms.NotifyConsumer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Bean for managing schedule by rest service and connecting to message broker
 */
@ManagedBean
@ViewScoped
@Getter
@Setter
public class ScheduleBean implements Serializable {

    @EJB
    private NotifyConsumer receiver;

    @Inject
    private SchceduleProducer schceduleProducer;

    private String station;

    public void setStation(String station) {
        this.station = station;
    }

    public void requestStations() {
        schceduleProducer.retrieveScheduleByStation(station);
    }

    public void checkQueue() throws Exception {
        try {
            receiver.createConnection();
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

