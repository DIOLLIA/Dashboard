package ru.timetable.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

public class JMSManager {

    public void sendMessage(final Serializable message) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("client-queue");
        MessageProducer producer = session.createProducer(queue);

        connection.start();

        ObjectMessage m1 = session.createObjectMessage();
        m1.setObject(message);

        producer.send(m1);

        connection.close();
    }

    public void sendMapMessage(final Map<String, String> data) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("client-queue");
        MessageProducer producer = session.createProducer(queue);

        connection.start();

        MapMessage m1 = session.createMapMessage();
        for (Map.Entry<String, String> stringStringEntry : data.entrySet()) {
            m1.setString(stringStringEntry.getKey(), stringStringEntry.getValue());
        }

        producer.send(m1);

        connection.close();
    }
}
