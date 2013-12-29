package de.agilecoders.wicket.flight.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.lang.Args;

/**
 * @author tfiwm
 */
public class Names {

    public static final String JS_CLASS_NAME = "js-fc";
    public static final String CLASS_NAME_SPLITTER = " ";
    public static final String COMPONENT_DATA_ATTRIBUTE_NAME = "data-fc";

    /**
     * flight component names are camel-case
     *
     * @param flightComponentName flight component name
     */
    public static void assertValid(String flightComponentName) {
        assertNotNull(flightComponentName);
        assertAlphanumeric(flightComponentName);
        assertCamelCase(flightComponentName);
    }

    public static void assertNotNull(String flightComponentName) {
        Args.notNull(flightComponentName, "flight component name");
    }

    public static void assertCamelCase(String flightComponentName) {
        Args.isTrue(flightComponentName.charAt(0) == flightComponentName.toUpperCase().charAt(0),
                    "flight component should be camelcase and start with an uppercase letter");
    }

    public static void assertAlphanumeric(String flightComponentName) {
        Args.isTrue(StringUtils.isAlphanumeric(flightComponentName),
                    "flight component should only contain alphanumeric character");
    }
}
