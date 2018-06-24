package ru.timetable.jsfbeans;


import lombok.Getter;
import lombok.Setter;
import ru.timetable.model.Schedule;
import ru.timetable.service.ScheduleService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class ScheduleBean implements Serializable {

    @Inject
    private ScheduleService scheduleService;

    private String station;
    private List<Schedule> scheduleList;
    private List<String> stationList;

    @PostConstruct
    void init() {
        scheduleList = scheduleService.retrieveAllScheduleBy();
        stationList = scheduleService.getStations();
    }

    public void requestStations() {
        scheduleList = scheduleService.retrieveScheduleByStation(station);
    }
}

