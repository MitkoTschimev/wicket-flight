package de.agilecoders.wicket.flight;

import de.agilecoders.wicket.flight.util.WicketBehaviorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;

import java.util.Collections;
import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 */
public class FlightBehavior extends Behavior {

    private final Map<String, IModel<String>> componentData;
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
        this.componentData = componentData;
        this.flightComponentName = flightComponentName;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);

        tag.append("class", "js-flight-component", " ");

        if (component.getApplication().usesDevelopmentConfig()) {
            WicketBehaviorUtils.assertCamelCase(flightComponentName);
        }

        tag.put("data-component", flightComponentName);

        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            tag.append("data-component-" + entry.getKey(), entry.getValue().getObject(), " ");
        }
    }


    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(WicketFlightJavascriptReference.instance()));
    }

    @Override
    public void detach(Component component) {
        super.detach(component);

        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            entry.getValue().detach();
        }
    }
}
