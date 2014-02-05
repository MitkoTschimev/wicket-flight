package de.agilecoders.wicket.flight.util;

import org.apache.wicket.util.lang.Args;

/**
 * @author tfiwm
 */
public class Names {

    public static final String JS_CLASS_NAME = "js-fc";
    public static final String CLASS_NAME_SPLITTER = " ";
    public static final String COMPONENT_CUSTOM_DATA_ATTRIBUTE_PREFIX = "data-fc";
    public static final String COMPONENT_DATA_ATTRIBUTE_SOURCE = "data-fcs";

    /**
     * flight component source can be the extracted name for standalone usage or
     * the require path to the source with requirejs
     *
     * @param flightComponentSource flight component source
     */
    public static void assertValid(String flightComponentSource) {
        assertNotNull(flightComponentSource);
    }

    public static void assertNotNull(String flightComponentSource) {
        Args.notNull(flightComponentSource, "flight component source");
    }
}
