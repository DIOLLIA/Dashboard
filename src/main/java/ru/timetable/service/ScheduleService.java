package ru.timetable.service;

import ru.timetable.model.Schedule;
import ru.timetable.repository.ScheduleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScheduleService {

    @Inject
    private ScheduleRepository scheduleRepository;

    public List<Schedule> retrieveAllScheduleBy() {
        return scheduleRepository.findAllOrderedByName();
    }

    public List<Schedule> retrieveScheduleByStation(String station) {
        return scheduleRepository.findByStationName(station);
    }

    public void save(Schedule schedule) {
        scheduleRepository.addNewScheduleItem(schedule);
    }
}
