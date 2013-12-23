package de.agilecoders.wicket.flight;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Link between Flight and Wicket which is automatically handled by the javascript library.
 */
public class WicketFlightManagerJavascriptReference extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    private static final class Holder {
        private static final WicketFlightManagerJavascriptReference instance = new WicketFlightManagerJavascriptReference();
    }

    static WicketFlightManagerJavascriptReference instance() {
        return Holder.instance;
    }

    /**
     * Construct.
     */
    public WicketFlightManagerJavascriptReference() {
        super(WicketFlightManagerJavascriptReference.class, "res/WicketFlightManager.js");
    }
}
