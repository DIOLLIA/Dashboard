package ru.timetable.jms;


import ru.timetable.model.Schedule;
import ru.timetable.service.ScheduleService;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Stateless
@LocalBean
public class ConsumerMessageListener implements MessageListener {

    @Inject
    private ScheduleService scheduleService;

    public void onMessage(Message message) {

        MapMessage mapMessage = (MapMessage) message;
        try {
            Schedule newSchedule = new Schedule();
            newSchedule.setStation(mapMessage.getString("station"));
            newSchedule.setArrivalTime(mapMessage.getString("arrTime"));
            newSchedule.setDepartureTime(mapMessage.getString("depTime"));
            newSchedule.setTrainNumber(mapMessage.getInt("train"));
            newSchedule.setDailyRouteId(mapMessage.getInt("dailyId"));
            newSchedule.setOrderNumber(mapMessage.getInt("orderNumber"));
            scheduleService.save(newSchedule);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
