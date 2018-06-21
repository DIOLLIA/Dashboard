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

    public Schedule findById(int id) {
        return em.find(Schedule.class, id);
    }

    /**
     * method find all schedule from database sorted by arrivalTime
     *
     * @return all schedule from database
     */
    public List<Schedule> findAllOrderedByName() {
        Query query = em.createQuery("FROM Schedule sch ORDER BY sch.arrivalTime");
        return query.getResultList();
    }
}
