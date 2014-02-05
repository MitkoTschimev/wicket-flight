package de.agilecoders.wicket.flight;

import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;

/**
 * Helper class for wicket flight and webjars resources
 *
 * @author tfiwm
 */
public final class WicketFlight {

    private static final MetaDataKey<WicketFlight> METADATA_KEY = new MetaDataKey<WicketFlight>() {
    };

    /**
     * installs wicket flight and webjars resource finder
     *
     * @param app the wicket application
     */
    public static void install(final Application app) {
        //app.setMetaData(METADATA_KEY, settings);

        WicketWebjars.install(app);
    }

    /**
     * private constructor.
     */
    private WicketFlight() {
        throw new UnsupportedOperationException();
    }

}
