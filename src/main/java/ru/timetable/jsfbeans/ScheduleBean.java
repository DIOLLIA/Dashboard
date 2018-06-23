package ru.timetable.jsfbeans;


import lombok.Getter;
import lombok.Setter;
import ru.timetable.service.ScheduleService;
import ru.timetable.jms.NotifyConsumer;
import ru.timetable.model.Schedule;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;

/**
 * Bean for managing schedule by rest service and connecting to message broker
 */
@ManagedBean
@ViewScoped
@Getter
@Setter
public class ScheduleBean implements Serializable {

    @Inject
    private NotifyConsumer receiver;

    @Inject
    private ScheduleService scheduleService;

    private String station;
    private List<Schedule> scheduleList;

    @PostConstruct
    void init() {
        scheduleList = scheduleService.retrieveAllScheduleBy();
    }

    public void requestStations() {
        scheduleList = scheduleService.retrieveScheduleByStation(station);
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

