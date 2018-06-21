package ru.timetable.data;

import ru.timetable.model.Schedule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class SchceduleProducer {

    @Inject
    private ScheduleRepository scheduleRepository;

    private List<Schedule> scheduleList;

    @Produces
    @Named
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }


    /**
     * Method view for changes on list and responce to jsf with actual information from DB
     *
     * @param schedule
     */
    public void onScheduleChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Schedule schedule) {
        retrieveAllScheduleBy();
    }

    @PostConstruct
    public void retrieveAllScheduleBy() {
        scheduleList = scheduleRepository.findAllOrderedByName();


    }

    public void retrieveScheduleByStation(String station) {
        scheduleList = scheduleRepository.findByStationName(station);
    }
}
