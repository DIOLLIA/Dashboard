package ru.timetable.repository;

import ru.timetable.model.Schedule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ScheduleRepository {
    @Inject
    private EntityManager em;

    /**
     * method find all schedule from database sorted by arrivalTime
     *
     * @return all schedule from database
     */
    public List<Schedule> findAllOrderedByName() {
        TypedQuery<Schedule> query = em.createQuery("SELECT sch FROM Schedule sch ORDER BY sch.arrivalTime", Schedule.class);
        return query.getResultList();
    }

    public List<Schedule> findByStationName(String station) {
        TypedQuery<Schedule> query = em.createQuery("SELECT sch FROM Schedule sch WHERE sch.station =:station ORDER BY sch.arrivalTime", Schedule.class);
        query.setParameter("station",station);
        return query.getResultList();
    }

    public void addNewScheduleItem(Schedule schedule) {
        em.persist(schedule);
    }

    public List<Schedule> checkTable(Schedule schedule) {
        TypedQuery<Schedule> query = em.createQuery("SELECT sch FROM Schedule sch WHERE sch.trainNumber =:trainNumber AND sch.dailyRouteId =:dailyRouteId AND sch.station =:station", Schedule.class);
        query.setParameter("trainNumber", schedule.getTrainNumber());
        query.setParameter("dailyRouteId", schedule.getDailyRouteId());
        query.setParameter("station", schedule.getStation());

        return query.getResultList();
    }
}
