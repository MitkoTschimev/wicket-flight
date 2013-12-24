package de.agilecoders.wicket.flight;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * A behaviour to connect wicket components with flight components.
 * <p/>
 * The Simple flight behavior is only usable if you have a component structure where the
 * Java, Js and Css file is in the same package hierarchy!
 * For more flexible usage: de.agilecoders.wicket.flight.FlightBehavior
 *
 * @author mtschimev
 */
public class SimpleFlightBehavior extends AbstractFlightBehavior {

    @Override
    protected String getComponentName(Component component) {
        return component.getClass().getSimpleName();
    }

    @Override
    protected void addComponentResourceReferences(Component component, IHeaderResponse response) {
        String componentName = getComponentName(component);
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(component.getClass(), componentName)));
        response.render(JavaScriptHeaderItem.forReference(new CssResourceReference(component.getClass(), componentName)));
    }
}
