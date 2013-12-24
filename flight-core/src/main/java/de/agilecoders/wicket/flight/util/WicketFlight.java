package de.agilecoders.wicket.flight.util;

import de.agilecoders.wicket.webjars.util.WicketWebjars;
import org.apache.wicket.Application;

/**
 * Helper class for wicket flight and webjars resources
 *
 * @author tfiwm
 */
public class WicketFlight {

    /**
     * installs wicket flight and webjars resource finder
     *
     * @param app the wicket application
     */
    public static void install(final Application app) {
        //TODO install wicket flight resource finder

        WicketWebjars.install(app);
    }

    /**
     * private constructor.
     */
    private WicketFlight() {
        throw new UnsupportedOperationException();
    }

}
