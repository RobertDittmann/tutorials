import com.tibco.tibjms.TibjmsTopicConnectionFactory;

import javax.jms.*;

public class TibcoTopicSubscriber {
    public static void main(String[] args) {
        final TibcoTopicSubscriber tibcoTopicSubscriber = new TibcoTopicSubscriber();
        try {
            tibcoTopicSubscriber.subscribe(1);
            tibcoTopicSubscriber.subscribe(2);
            tibcoTopicSubscriber.subscribe(3);
        } catch (final JMSException e) {
            e.printStackTrace();
        }
    }

    private void subscribe(final int subscriberNumber) throws JMSException {
        final TibjmsTopicConnectionFactory tibjmsTopicConnectionFactory = new TibjmsTopicConnectionFactory(TibcoConnectionConfiguration.getUrl());
        final TopicConnection topicConnection = tibjmsTopicConnectionFactory.createTopicConnection(TibcoConnectionConfiguration.getUser(), TibcoConnectionConfiguration.getPassword());
        final TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        final Topic topic = topicSession.createTopic(TibcoConnectionConfiguration.getTibcoTopic());
        final TopicSubscriber subscriber = topicSession.createSubscriber(topic);
        subscriber.setMessageListener(new TopicListener(subscriberNumber));
        topicConnection.start();
    }


    private class TopicListener implements MessageListener {
        private final int subscriberNumber;

        TopicListener(final int subscriberNumber) {
            this.subscriberNumber = subscriberNumber;
        }

        @Override
        public void onMessage(final Message msg) {
            try {
                System.out.println("Subscriber " + subscriberNumber + ", received message " + msg.getStringProperty("props"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
