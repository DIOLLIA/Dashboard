package ru.timetable.jms;


import ru.timetable.model.Schedule;
import ru.timetable.service.ScheduleService;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;


public class ConsumerMessageListener implements MessageListener {
    private ScheduleService scheduleService;

    public ConsumerMessageListener(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

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
