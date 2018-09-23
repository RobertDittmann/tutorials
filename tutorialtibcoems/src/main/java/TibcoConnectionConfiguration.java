import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

class TibcoConnectionConfiguration {
    private static final String CONNECTION_PROPERTIES = "tibco.properties";
    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        try {
            final InputStream resourceAsStream = TibcoConnectionConfiguration.class.getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES);
            if (Objects.isNull(resourceAsStream)) {
                throw new IOException("Could not find " + CONNECTION_PROPERTIES);
            }
            final Properties props = new Properties();
            props.load(resourceAsStream);
            return props;
        } catch (final IOException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    static String getUser() {
        return PROPERTIES.getProperty(PROPS.USER.getPropertyName());
    }

    static String getPassword() {
        return PROPERTIES.getProperty(PROPS.PASSWORD.getPropertyName());
    }

    static String getUrl() {
        return PROPERTIES.getProperty(PROPS.TIBCO_URL.getPropertyName());
    }

    static String getAdmin() {
        return PROPERTIES.getProperty(PROPS.ADMIN.getPropertyName());
    }

    static String getAdminPassword() {
        return PROPERTIES.getProperty(PROPS.ADMIN_PASSWORD.getPropertyName());
    }

    static String getTibcoQueue() {
        return PROPERTIES.getProperty(PROPS.TIBCO_QUEUE.getPropertyName());
    }

    static String getTibcoTopic() {
        return PROPERTIES.getProperty(PROPS.TIBCO_TOPIC.getPropertyName());
    }

    private enum PROPS {
        USER("user"),
        PASSWORD("password"),
        ADMIN("admin"),
        ADMIN_PASSWORD("admin.password"),
        TIBCO_URL("tibco.url"),
        TIBCO_QUEUE("tibco.queue"),
        TIBCO_TOPIC("tibco.topic");

        private final String propertyName;

        PROPS(final String value) {
            this.propertyName = value;
        }

        public String getPropertyName() {
            return propertyName;
        }
    }
}
