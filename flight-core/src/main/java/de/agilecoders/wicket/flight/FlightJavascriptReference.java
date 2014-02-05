package de.agilecoders.wicket.flight;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Extends the defineComponent method from flight with the possibility to extend components and mixins
 */
public class FlightJavascriptReference extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final FlightJavascriptReference instance = new FlightJavascriptReference();
    }

    static FlightJavascriptReference instance() {
        return Holder.instance;
    }

    /**
     * Construct.
     */
    public FlightJavascriptReference() {
        super(FlightJavascriptReference.class, "res/flight.standalone.js");
    }
}
