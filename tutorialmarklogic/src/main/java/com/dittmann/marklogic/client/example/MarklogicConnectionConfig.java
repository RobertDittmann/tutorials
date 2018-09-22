package com.dittmann.marklogic.client.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

class MarklogicConnectionConfig {

    private static final String CONNECTION_PROPERTIES = "connection.properties";
    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        try {
            final InputStream resourceAsStream = MarklogicConnectionConfig.class.getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES);
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

    static String getHost() {
        return PROPERTIES.getProperty(PROPS.HOST.getPropertyName());
    }

    static int getPort() {
        return Integer.parseInt(PROPERTIES.getProperty(PROPS.PORT.getPropertyName()));
    }

    private enum PROPS {
        USER("marklogic.user"),
        PASSWORD("marklogic.password"),
        HOST("marklogic.host"),
        PORT("marklogic.port");

        private final String propertyName;

        PROPS(final String value) {
            this.propertyName = value;
        }

        public String getPropertyName() {
            return propertyName;
        }
    }
}
