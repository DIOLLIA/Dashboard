package ru.timetable.jms;

import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.MapMessage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageSenderImpl implements MessageSender {
    @Override
    public void startUp() throws Exception {
        JMSManager jmsManager = new JMSManager();
        Map<String, String> data = new HashMap<>();
        data.put("action", "startup");
        data.put("uuid", UUID.randomUUID().toString());
        jmsManager.sendMapMessage(data);

    }
}
