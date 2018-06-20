package ru.timetable.service;

import org.hibernate.Session;
import ru.timetable.model.Schedule;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ScheduleRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Schedule> scheduleEvent;

    public void addNewSchedule(Schedule schedule) {
        log.info("Registering new schedule with station: " + schedule.getStation() + ", train number:  " + schedule.getTrainNumber());
        Session session = (Session) em.getDelegate();
        session.persist(schedule);
        scheduleEvent.fire(schedule);
    }
    public List<Schedule> checkTable(Schedule schedule){
        Query query=em.createQuery("FROM Schedule sch WHERE sch.trainNumber =:trainNumber AND sch.dailyRouteId =:dailyRouteId AND sch.station =:station");
        query.setParameter("trainNumber", schedule.getTrainNumber());
        query.setParameter("dailyRouteId",schedule.getDailyRouteId());
        query.setParameter("station",schedule.getStation());

        return query.getResultList();
    }
}
