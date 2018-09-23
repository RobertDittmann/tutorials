import com.tibco.tibjms.TibjmsQueueConnectionFactory;

import javax.jms.*;

public class TibcoQueueConsumer {

    public static void main(String[] args) throws JMSException {
        final TibjmsQueueConnectionFactory factory = new TibjmsQueueConnectionFactory(TibcoConnectionConfiguration.getUrl());
        final QueueConnection queueConnection = factory.createQueueConnection(TibcoConnectionConfiguration.getUser(), TibcoConnectionConfiguration.getPassword());

        final Session session = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        final Queue outputQueue = session.createQueue(TibcoConnectionConfiguration.getTibcoQueue());
        final MessageConsumer msgConsumer = session.createConsumer(outputQueue);
        msgConsumer.setMessageListener(new QueueListener());
        queueConnection.start();
    }

    private static class QueueListener implements MessageListener {
        @Override
        public void onMessage(final Message msg) {
            try {
                System.out.println(msg.getStringProperty("props"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
