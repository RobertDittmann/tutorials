import com.tibco.tibjms.TibjmsQueueConnectionFactory;

import javax.jms.*;

public class TibcoQueueProducer {
    public static void main(String[] args) throws JMSException {
        final TibjmsQueueConnectionFactory factory = new TibjmsQueueConnectionFactory(TibcoConnectionConfiguration.getUrl());
        final QueueConnection queueConnection = factory.createQueueConnection(TibcoConnectionConfiguration.getUser(), TibcoConnectionConfiguration.getPassword());
        final Session queueSession = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue outputQueue = queueSession.createQueue(TibcoConnectionConfiguration.getTibcoQueue());
        final MessageProducer msgProducer = queueSession.createProducer(outputQueue);
        queueConnection.start();

        final Message queueMessage = queueSession.createMessage();

        for (int i = 0; i < 15; i++) {
            queueMessage.setStringProperty("props", "queTest" + i);
            System.out.println(queueMessage);
            msgProducer.send(queueMessage);
        }
        queueConnection.stop();
        queueConnection.close();
    }
}
