package de.agilecoders.wicket.flight;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.Collections;
import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 * <p/>
 * The Simple flight behavior is only usable if you have a component structure where the
 * Java, Js and Css files are in the same package hierarchy!
 * For more flexible usage: de.agilecoders.wicket.flight.FlightBehavior
 *
 * @author mtschimev
 */
public class SimpleFlightBehavior extends AbstractFlightBehavior {

    private final boolean loadCssResourceReference;

    public SimpleFlightBehavior() {
        this(false, Collections.<String, IModel<String>>emptyMap());
    }

    public SimpleFlightBehavior(Map<String, IModel<String>> componentData) {
        this(false, componentData);
    }

    public SimpleFlightBehavior(boolean loadCssResourceReference) {
        this(loadCssResourceReference, Collections.<String, IModel<String>>emptyMap());
    }

    /**
     * Creates a flight behavior which loads the resource references from the same package hierarchy
     *
     * @param loadCssResourceReference load cssResourceReference
     * @param componentData map of models which are containing data for the flight component default Attributes
     */
    public SimpleFlightBehavior(boolean loadCssResourceReference, Map<String, IModel<String>> componentData) {
        super(componentData);
        this.loadCssResourceReference = loadCssResourceReference;
    }

    @Override
    protected String getComponentName(Component component) {
        return component.getClass().getSimpleName();
    }

    @Override
    protected void addComponentResourceReferences(Component component, IHeaderResponse response) {
        String componentName = getComponentName(component);
        if(loadCssResourceReference) {
            response.render(CssHeaderItem.forReference(
                    new CssResourceReference(component.getClass(), String.format("%s.css", componentName))
            ));
        }
        response.render(JavaScriptHeaderItem.forReference(
                new JavaScriptResourceReference(component.getClass(), String.format("%s.js", componentName))
        ));
    }
}
