package ru.timetable.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import ru.timetable.model.Schedule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ScheduleRepository {
    @Inject
    private EntityManager em;

    public Schedule findById(int id) {
        return em.find(Schedule.class, id);
    }
//todo КАК ТЫ СОБРАЛСЯ СОРТИРОВАТЬ ПО СТРИНГЕ (ВРЕМЯ)???

    /**
     * Возвращает список отсортированный по arrivalTime
     *
     * @return
     */
    public List<Schedule> findAllOrderedByName() {
        // using Hibernate Session and Criteria Query via Hibernate Native API
        Session session = (Session) em.getDelegate();
        Criteria cb = session.createCriteria(Schedule.class);
//        Criteria cb = session.createQuery("FROM Shcedule sch") //todo попробуй селсть запрос
        cb.addOrder(Order.asc("arrivalTime"));
        return (List<Schedule>) cb.list();
        // return members;
    }
}
