package de.agilecoders.wicket.flight;

import de.agilecoders.wicket.requirejs.RequireJsResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends the defineComponent method from flight with the possibility to extend components and mixins
 */
public class FlightJavascriptReference extends JQueryPluginResourceReference {
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
        super(FlightJavascriptReference.class, "res/flight.js");
    }

    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        List<HeaderItem> dependencies = new ArrayList<>();

        dependencies.add(JavaScriptHeaderItem.forReference(new RequireJsResourceReference()));

        return dependencies;
    }
}
