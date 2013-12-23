package de.agilecoders.wicket.flight;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import java.util.HashMap;
import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 */
public class FlightBehavior extends Behavior {


    private final Map<String, IModel<String>> componentData;
    private final String flightComponentName;

    /**
     * Construct.
     *
     * @param flightComponentName the name of the flight component
     */
    public FlightBehavior(String flightComponentName) {
        this(flightComponentName, new HashMap<String, IModel<String>>());
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
        tag.put("data-component", checkIsCamelCase(flightComponentName));

        for (String key : componentData.keySet()) {
            tag.append("data-component-" + key, componentData.get(key).getObject(), " ");
        }
    }

    /**
     * flight component names are camelcase
     *
     * @param flightComponentName flight component name
     * @return the name valid
     */
    private static String checkIsCamelCase(String flightComponentName) {
        Args.notNull(flightComponentName, "flight component name");
        Args.isTrue(flightComponentName.charAt(0) == flightComponentName.toUpperCase().charAt(0), "flight component should be camelcase and start with an uppercase letter");
        Args.isTrue(StringUtils.isAlphanumeric(flightComponentName), "flight component should only contain alphanumeric character");
        return flightComponentName;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(new WicketFlightJavascriptReference()));
    }

    @Override
    public void detach(Component component) {
        super.detach(component);

        for (String key : componentData.keySet()) {
            componentData.get(key).detach();
        }
    }
}
