package de.agilecoders.wicket.flight;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.Args;

import java.util.Collections;
import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 */
public class FlightBehavior extends Behavior {

    private static final ResourceReference flightReference = new WicketFlightJavascriptReference();
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
            assertCamelCase(flightComponentName);
        }

        tag.put("data-component", flightComponentName);

        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            tag.append("data-component-" + entry.getKey(), entry.getValue().getObject(), " ");
        }
    }

    /**
     * flight component names are camel-case
     *
     * @param flightComponentName flight component name
     */
    private static void assertCamelCase(String flightComponentName) {
        Args.notNull(flightComponentName, "flight component name");
        Args.isTrue(flightComponentName.charAt(0) == flightComponentName.toUpperCase().charAt(0), "flight component should be camelcase and start with an uppercase letter");
        Args.isTrue(StringUtils.isAlphanumeric(flightComponentName), "flight component should only contain alphanumeric character");
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(flightReference));
    }

    @Override
    public void detach(Component component) {
        super.detach(component);

        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            entry.getValue().detach();
        }
    }
}
