package de.agilecoders.wicket.flight.settings;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.flight.WicketFlight;
import de.agilecoders.wicket.requirejs.RequireJsHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

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
        // TODO: use flight from webjars if standalone is available. (there's also a new release version 1.1.2)
        this.flightJavaScriptResourceReference = new JavaScriptResourceReference(WicketFlight.class, "res/flight.standalone.js") {
            @Override
            public Iterable<? extends HeaderItem> getDependencies() {
                List<HeaderItem> dependencies = new ArrayList<>();
                dependencies.add(new RequireJsHeaderItem());

                return dependencies;
            }
        };
        this.wicketFlightJavaScriptResourceReference = new JavaScriptResourceReference(WicketFlight.class, "res/WicketFlight.js") {
            @Override
            public Iterable<? extends HeaderItem> getDependencies() {
                final List<HeaderItem> dependencies = Lists.newArrayList(super.getDependencies());
                dependencies.add(JavaScriptHeaderItem.forReference(flightJavaScriptResourceReference()));
                return dependencies;
            }
        };
    }

    @Override
    public ResourceReference flightJavaScriptResourceReference() {
        return this.flightJavaScriptResourceReference;
    }

    @Override
    public ResourceReference wicketFlightJavaScriptResourceReference() {
        return wicketFlightJavaScriptResourceReference;
    }
}
