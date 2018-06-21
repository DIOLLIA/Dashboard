package ru.timetable.data;

import ru.timetable.model.Schedule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query query = em.createQuery("FROM Schedule sch ORDER BY sch.arrivalTime");
        return query.getResultList();
    }

    public List<Schedule> findByStationName(String station) {
        Query query = em.createQuery("FROM Schedule sch WHERE sch.station =:station ORDER BY sch.arrivalTime");
        query.setParameter("station",station);
        return query.getResultList();
    }
}
