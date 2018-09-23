import com.tibco.tibjms.TibjmsTopicConnectionFactory;

import javax.jms.*;

public class TibcoTopicPublisher {
    public static void main(String[] args) throws JMSException {
        final TibjmsTopicConnectionFactory tibjmsTopicConnectionFactory = new TibjmsTopicConnectionFactory(TibcoConnectionConfiguration.getUrl());
        final TopicConnection topicConnection = tibjmsTopicConnectionFactory.createTopicConnection(TibcoConnectionConfiguration.getUser(), TibcoConnectionConfiguration.getPassword());
        final TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        final Topic topic = topicSession.createTopic(TibcoConnectionConfiguration.getTibcoTopic());
        final TopicPublisher publisher = topicSession.createPublisher(topic);
        final Message messageTopic = topicSession.createMessage();
        topicConnection.start();
        messageTopic.setStringProperty("props", "topicTest");
        publisher.publish(messageTopic);
        publisher.close();
    }
}
