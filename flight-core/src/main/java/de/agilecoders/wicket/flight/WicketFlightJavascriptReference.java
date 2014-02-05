package de.agilecoders.wicket.flight;

import com.google.common.collect.Lists;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.settings.IJavaScriptLibrarySettings;

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

        if (Application.exists()) {
            IJavaScriptLibrarySettings javaScriptLibrarySettings = Application.get().getJavaScriptLibrarySettings();
            dependencies.add(JavaScriptHeaderItem.forReference(javaScriptLibrarySettings.getWicketEventReference()));
        } else {
            dependencies.add(JavaScriptHeaderItem.forReference(WicketEventJQueryResourceReference.get()));
        }

        dependencies.add(JavaScriptHeaderItem.forReference(Holder.flight));

        return dependencies;
    }
}
