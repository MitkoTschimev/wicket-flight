package de.agilecoders.wicket.flight.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.lang.Args;

/**
 * @author tfiwm
 */
public class WicketBehaviorUtils {

    /**
     * flight component names are camel-case
     *
     * @param flightComponentName flight component name
     */
    public static void assertCamelCase(String flightComponentName) {
        Args.notNull(flightComponentName, "flight component name");
        Args.isTrue(flightComponentName.charAt(0) == flightComponentName.toUpperCase().charAt(0), "flight component should be camelcase and start with an uppercase letter");
        Args.isTrue(StringUtils.isAlphanumeric(flightComponentName), "flight component should only contain alphanumeric character");
    }
}
