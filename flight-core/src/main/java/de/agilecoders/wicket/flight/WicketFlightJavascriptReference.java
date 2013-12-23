package de.agilecoders.wicket.flight;

import com.google.common.collect.Lists;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.List;

/**
 * Extends the defineComponent method from flight with the possibility to extend components and mixins
 */
public class WicketFlightJavascriptReference extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     */
    public WicketFlightJavascriptReference() {
        super(WicketFlightJavascriptReference.class, "res/WicketFlight.js");
    }

    /**
     *
     * @return a list of header items containing the javascript references
     */
    @Override
    public Iterable<? extends HeaderItem> getDependencies() {
        final List<HeaderItem> dependencies = Lists.newArrayList(super.getDependencies());

        dependencies.add(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("flight/flight-1.0.9/flight.min.js")));
        dependencies.add(JavaScriptHeaderItem.forReference(new WicketFlightManagerJavascriptReference()));

        return dependencies;
    }
}
