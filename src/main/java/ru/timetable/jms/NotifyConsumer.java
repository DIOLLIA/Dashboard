/*
package com.tsystems.jms;

import lombok.Getter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

*/
/**
 * Created by bugav on 22.11.2017.
 *//*

@Singleton
@Startup
@Getter
public class NotifyConsumer {

    private ConnectionFactory connectionFactory;
    private Connection connection;

    @PostConstruct
    public void init() {
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    }

    */
/**
     * creates connection to active mq
     *//*

    public void createConnection() throws JMSException {
        connection = connectionFactory.createConnection();
        connection.start();
    }

    */
/**
     * recieves jms message
     *//*

    public void receive() {

        Session session = null;





        try {

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            String subject = "Bugaved";
            Destination destination = session.createQueue(subject);

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
    */
/**
     * close connection to active mq
     *//*

    public void closeConnection() throws JMSException {
        connection.close();
    }

}
*/

package ru.timetable.jms;

import lombok.Getter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;


@Singleton
@Startup
@Getter
public class NotifyConsumer {

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

    public void receive()throws NamingException {

        try {

            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            QueueReceiver receiver = session.createReceiver(queue);
            receiver.setMessageListener(new ConsumerMessageListener("Table"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

}

