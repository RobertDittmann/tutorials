import com.tibco.tibjms.TibjmsQueueConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.*;

public class TibcoQueueWithCamelRoute {

    private static final String QUEUE_NAME_SEND = "testCamelQueueSend";
    private static final String QUEUE_NAME_READ = "testCamelQueueRead";

    public static void main(String[] args) throws Exception {
        final TibjmsQueueConnectionFactory factory = new TibjmsQueueConnectionFactory(TibcoConnectionConfiguration.getUrl());
        final QueueConnection queueConnection = factory.createQueueConnection(TibcoConnectionConfiguration.getUser(), TibcoConnectionConfiguration.getPassword());
        final Session queueSession = queueConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue outputQueue = queueSession.createQueue(QUEUE_NAME_SEND);
        final MessageProducer msgProducer = queueSession.createProducer(outputQueue);
        queueConnection.start();

        final Message queueMessage = queueSession.createMessage();
        for (int i = 0; i < 15; i++) {
            queueMessage.setStringProperty("props", "queTest: " + i);
            msgProducer.send(queueMessage);
            System.out.println("SEND MESSAGE: " + queueMessage);
        }
        queueConnection.stop();

        final CamelContext context = new DefaultCamelContext();
        final ConnectionFactory connectionFactory = new TibjmsQueueConnectionFactory(
                TibcoConnectionConfiguration.getUrl());
        context.addComponent("test-jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.addRoutes(new Camel1stIteration());
        context.addRoutes(new Camel2ndIteration());
        context.start();
        Thread.sleep(2000);
        context.stop();
    }


    static class Camel1stIteration extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("test-jms:queue:" + QUEUE_NAME_SEND)
                    .process(new ModifyAndSendJms())
                    .to("test-jms:queue:" + QUEUE_NAME_READ);
        }

    }

    static class Camel2ndIteration extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("test-jms:queue:" + QUEUE_NAME_READ).process(new ReadFinalJms());
        }
    }

    private static class ModifyAndSendJms implements org.apache.camel.Processor {
        @Override
        public void process(final Exchange exchange) throws Exception {
            System.out.println("READ: " + exchange.getIn().getHeaders().get("props").toString());
            exchange.getIn().setHeader("props", exchange.getIn().getHeaders().get("props").toString() + " AFTER CHANGE");
        }
    }

    private static class ReadFinalJms implements org.apache.camel.Processor {
        @Override
        public void process(final Exchange exchange) throws Exception {
            System.out.println("READ AFTER CHANGE: " + exchange.getIn().getHeaders().get("props").toString());
        }
    }
}
