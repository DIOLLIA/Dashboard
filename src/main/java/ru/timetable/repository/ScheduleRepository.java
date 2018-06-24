package ru.timetable.repository;

import ru.timetable.model.Schedule;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class ScheduleRepository {
    @PersistenceContext
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
        query.setParameter("station", station);
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

    public List<String> getStations() {
        TypedQuery<String> query = em.createQuery("SELECT DISTINCT sch.station FROM Schedule sch", String.class);
        return query.getResultList();
    }
}
