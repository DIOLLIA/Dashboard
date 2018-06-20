package ru.timetable.jms;


import ru.timetable.model.Schedule;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;


public class ConsumerMessageListener implements MessageListener {
    private String consumerName;
    static List<Schedule> scheduleList = new ArrayList<>();

    public ConsumerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    public void onMessage(Message message) {
        if (scheduleList==null){
            scheduleList=new ArrayList<>();
        }
            MapMessage mapMessage = (MapMessage) message;
        try {
            Schedule newSchedule = new Schedule();
            newSchedule.setStation(mapMessage.getString("station"));
            newSchedule.setArrivalTime(mapMessage.getString("arrTime"));
            newSchedule.setDepartureTime(mapMessage.getString("depTime"));
            newSchedule.setTrainNumber(mapMessage.getInt("train"));
            newSchedule.setDailyRouteId(mapMessage.getInt("dailyId"));
            newSchedule.setOrderNumber(mapMessage.getInt("orderNumber"));
            scheduleList.add(newSchedule);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

/*    public Schedule getNewSchedule() {
        return newSchedule;
    }

    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule = newSchedule;
    }*/


    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        ConsumerMessageListener.scheduleList = scheduleList;
    }
}
