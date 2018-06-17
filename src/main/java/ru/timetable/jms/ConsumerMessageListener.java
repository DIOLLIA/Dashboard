package ru.timetable.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class ConsumerMessageListener implements MessageListener {
    private String consumerName;
    public ConsumerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            System.out.println(((MapMessage) message).getString("Station"));
            System.out.println(consumerName + " received "

                    + mapMessage.getInt("train"));

        } catch (JMSException e) {

            e.printStackTrace();

        }

    }

}
