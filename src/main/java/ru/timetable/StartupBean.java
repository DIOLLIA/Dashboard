package ru.timetable;

import org.apache.activemq.ActiveMQConnection;
import ru.timetable.jms.ConsumerMessageListener;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@Singleton
@LocalBean
@Startup
public class StartupBean {

    @Inject
    private ConsumerMessageListener consumerMessageListener;

    @PostConstruct
    void init() {
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", ActiveMQConnection.DEFAULT_BROKER_URL);
        props.put("queue.timetable-queue", "timetable-queue");
        props.put("queue.client-queue", "client-queue");
        props.put("connectionFactoryNames", "timetable");
        Context context;
        try {
            context = new InitialContext(props);
            QueueConnectionFactory queueFactory = (QueueConnectionFactory) context.lookup("timetable");
            Queue queueTimetable = (Queue) context.lookup("timetable-queue");
            Queue queueClient = (Queue) context.lookup("client-queue");
            QueueConnection connection = queueFactory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            // Send first message to main server
            MessageProducer producer = session.createProducer(queueClient);
            MapMessage m1 = session.createMapMessage();
            m1.setString("action", "startup");
            producer.send(m1);


            QueueReceiver receiver = session.createReceiver(queueTimetable);

            receiver.setMessageListener(consumerMessageListener);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }
}
