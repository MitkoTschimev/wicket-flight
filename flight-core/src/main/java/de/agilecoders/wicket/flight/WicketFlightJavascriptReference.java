package de.agilecoders.wicket.flight;

import com.google.common.collect.Lists;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.List;

/**
 * Extends the defineComponent method from flight with the possibility to extend components and mixins
 */
public class WicketFlightJavascriptReference extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final WicketFlightJavascriptReference instance = new WicketFlightJavascriptReference();
//        private static final WebjarsJavaScriptResourceReference flight = new WebjarsJavaScriptResourceReference("flight/current/standalone/build.js");
        //TODO remove FlightJavascriptReference if webjars provides flight as working library
        private static final FlightJavascriptReference flight = FlightJavascriptReference.instance();
    }

    static WicketFlightJavascriptReference instance() {
        return Holder.instance;
    }

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

        dependencies.add(JavaScriptHeaderItem.forReference(Holder.flight));
        dependencies.add(JavaScriptHeaderItem.forReference(WicketFlightManagerJavascriptReference.instance()));

        return dependencies;
    }
}
