package ru.timetable;

import org.apache.activemq.ActiveMQConnection;
import ru.timetable.service.ScheduleService;
import ru.timetable.jms.ConsumerMessageListener;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@Startup
@Singleton
public class StartupBean {

    @Inject
    private ScheduleService scheduleService;

    @PostConstruct
    void init() {

        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", ActiveMQConnection.DEFAULT_BROKER_URL);
        props.put("queue.js-queue", "timetable");
        props.put("connectionFactoryNames", "timetable");
        Context context = null;
        try {
            context = new InitialContext(props);
            QueueConnectionFactory queueFactory = (QueueConnectionFactory) context.lookup("timetable");
            Queue queue = (Queue) context.lookup("js-queue");
            QueueConnection connection = queueFactory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            QueueReceiver receiver = session.createReceiver(queue);

            receiver.setMessageListener(new ConsumerMessageListener(scheduleService));
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }
}
