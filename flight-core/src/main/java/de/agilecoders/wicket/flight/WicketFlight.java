package de.agilecoders.wicket.flight;

import de.agilecoders.wicket.flight.settings.IWicketFlightSettings;
import de.agilecoders.wicket.flight.settings.WicketFlightSettings;
import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;

/**
 * Helper class for wicket flight and webjars resources
 *
 * @author tfiwm
 */
public final class WicketFlight {

    private static final MetaDataKey<IWicketFlightSettings> METADATA_KEY = new MetaDataKey<IWicketFlightSettings>() {
    };

    /**
     * installs wicket flight and webjars resource finder
     *
     * @param app the wicket application
     */
    public static void install(final Application app) {
        install(app, new WicketFlightSettings());
    }

    /**
     * installs wicket flight and webjars resource finder
     *
     * @param app      the wicket application
     * @param settings the settings to use
     */
    public static void install(final Application app, IWicketFlightSettings settings) {
        if (settings(app) == null) {
            app.setMetaData(METADATA_KEY, settings);

            WicketWebjars.install(app);
        }
    }

    /**
     * @return the wicket flight settings assigned to current thread
     */
    public static IWicketFlightSettings settings() {
        if (Application.exists()) {
            return settings(Application.get());
        }

        throw new IllegalStateException("there's no application assigned to this thread.");
    }

    /**
     * @param application the application to read settings from
     * @return the wicket flight settings assigned to given application
     */
    public static IWicketFlightSettings settings(final Application application) {
        return application.getMetaData(METADATA_KEY);
    }

    /**
     * private constructor.
     */
    private WicketFlight() {
        throw new UnsupportedOperationException();
    }

}
