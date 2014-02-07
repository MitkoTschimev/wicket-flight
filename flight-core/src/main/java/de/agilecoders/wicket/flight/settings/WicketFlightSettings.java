package de.agilecoders.wicket.flight.settings;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.flight.WicketFlight;
import de.agilecoders.wicket.requirejs.RequireJsHeaderItem;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

import java.util.ArrayList;
import java.util.List;

/**
 * default settings implementation
 *
 * @author miha
 */
public class WicketFlightSettings implements IWicketFlightSettings {

    private final ResourceReference flightJavaScriptResourceReference;
    private final ResourceReference wicketFlightJavaScriptResourceReference;

    /**
     * Construct.
     */
    public WicketFlightSettings() {
        this.flightJavaScriptResourceReference = new FlightResourceReference();
        this.wicketFlightJavaScriptResourceReference = new WicketFlightResourceReference(this);
    }

    @Override
    public ResourceReference flightJavaScriptResourceReference() {
        return this.flightJavaScriptResourceReference;
    }

    @Override
    public ResourceReference wicketFlightJavaScriptResourceReference() {
        return wicketFlightJavaScriptResourceReference;
    }

    /**
     * flight resource reference.
     */
    protected static class FlightResourceReference extends WebjarsJavaScriptResourceReference {

        /**
         * Construct.
         */
        public FlightResourceReference() {
            super("flight/current/flight.min.js");
        }

        @Override
        public Iterable<? extends HeaderItem> getDependencies() {
            List<HeaderItem> dependencies = new ArrayList<>();
            dependencies.add(new RequireJsHeaderItem());

            return dependencies;
        }
    }

    /**
     * wicket flight resource reference.
     */
    protected static class WicketFlightResourceReference extends JavaScriptResourceReference {

        private final IWicketFlightSettings settings;

        /**
         * Construct.
         */
        public WicketFlightResourceReference(final IWicketFlightSettings settings) {
            super(WicketFlight.class, "res/WicketFlight.js");

            this.settings = settings;
        }

        @Override
        public Iterable<? extends HeaderItem> getDependencies() {
            final List<HeaderItem> dependencies = Lists.newArrayList(super.getDependencies());

            if (Application.exists()) {
                IJavaScriptLibrarySettings javaScriptLibrarySettings = Application.get().getJavaScriptLibrarySettings();
                dependencies.add(JavaScriptHeaderItem.forReference(javaScriptLibrarySettings.getWicketEventReference()));
            } else {
                dependencies.add(JavaScriptHeaderItem.forReference(WicketEventJQueryResourceReference.get()));
            }

            dependencies.add(JavaScriptHeaderItem.forReference(settings.flightJavaScriptResourceReference()));

            return dependencies;
        }
    }
}
