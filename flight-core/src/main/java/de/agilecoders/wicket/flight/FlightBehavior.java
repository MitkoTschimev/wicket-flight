package de.agilecoders.wicket.flight;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import java.util.Collections;
import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 */
public abstract class FlightBehavior extends AbstractFlightBehavior {

    private final String flightComponentName;

    /**
     * Construct, uses an empty custom component data map.
     *
     * @param flightComponentName the name of the flight component
     */
    public FlightBehavior(String flightComponentName) {
        this(flightComponentName, Collections.<String, IModel<String>>emptyMap());
    }

    /**
     * Construct.
     *
     * @param flightComponentName the name of the flight component
     * @param componentData       custom data for the frontend part
     */
    public FlightBehavior(String flightComponentName, Map<String, IModel<String>> componentData) {
        super(componentData);
        this.flightComponentName = flightComponentName;
    }

    @Override
    protected String getComponentName(Component component) {
        return this.flightComponentName;
    }
}
