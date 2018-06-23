
package ru.timetable.jms;

import lombok.Getter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import ru.timetable.repository.ScheduleRepository;
import ru.timetable.model.Schedule;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;


@Singleton
@Startup
@Getter
public class NotifyConsumer {
    @Inject
    private ScheduleRepository scheduleRepository;

    private QueueConnectionFactory connectionFactory;
    private QueueConnection connection;
    private Queue queue;

    @PostConstruct
    public void init() {

        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    }

    public void createConnection() throws JMSException, NamingException {
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "timetable");
        props.put("connectionFactoryNames", "timetable");
        Context context = new InitialContext(props);
        connectionFactory = (QueueConnectionFactory) context.lookup("timetable");
        queue = (Queue) context.lookup("js-queue");
        connection = connectionFactory.createQueueConnection();
        connection.start();
    }

    public void receive() throws NamingException {

    }

    public void closeConnection() throws JMSException {
        connection.close();
    }
}

