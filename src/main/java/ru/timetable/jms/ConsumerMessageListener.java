package ru.timetable.jms;


import ru.timetable.model.Schedule;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;


public class ConsumerMessageListener implements MessageListener {
    private String consumerName;

    public static Schedule newSchedule=null;

    public ConsumerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            newSchedule = new Schedule();
            newSchedule.setStation(mapMessage.getString("station"));
            newSchedule.setTrainNumber(mapMessage.getInt("train"));
            newSchedule.setDailyRouteId(mapMessage.getInt("dailyId"));
            newSchedule.setDepartureTime(mapMessage.getString("depTime"));
            newSchedule.setArrivalTime(mapMessage.getString("arrTime"));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Schedule getNewSchedule() {
        return newSchedule;
    }

    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule = newSchedule;
    }
}
