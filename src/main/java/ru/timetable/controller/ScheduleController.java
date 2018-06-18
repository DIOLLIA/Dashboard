package ru.timetable.controller;

import ru.timetable.model.Schedule;
import ru.timetable.service.ScheduleRegistration;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ScheduleController {
    @Inject
    private FacesContext facesContext;

    @Inject
    private ScheduleRegistration scheduleRegistration;

    private Schedule newSchedule;

    @Produces
    @Named
    public Schedule getNewSchedule() {
        return newSchedule;
    }

    public void add() {
        try {
            scheduleRegistration.addNewSchedule(newSchedule);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Successful!"));
            addNewSchedule();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    errorMessage, "Unsuccessful!"));
        }
    }

    @PostConstruct
    public void addNewSchedule() {
        newSchedule = new Schedule();
    }

    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Updating schedule failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }
}
