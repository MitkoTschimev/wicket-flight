package de.agilecoders.wicket.flight;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import de.agilecoders.wicket.flight.util.Names;

/**
 * @author mtschimev
 */
public abstract class AbstractFlightBehavior extends Behavior {

    private final Map<String, IModel<String>> componentData;

    /**
     * Construct, uses an empty custom component data map.
     */
    public AbstractFlightBehavior() {
        this(null);
    }

    /**
     * Construct.
     *
     * @param componentData map of models which are containing data for the flight component default Attributes
     */
    public AbstractFlightBehavior(Map<String, IModel<String>> componentData) {
        this.componentData = componentData;
    }

    @Override
    public void bind(Component component) {
        super.bind(component);

        if (component.getApplication().usesDevelopmentConfig()) {
            Names.assertValid(getComponentSource(component));
        }
    }

    /**
     * Converts the map to html data attributes for the flight component
     *
     * @param tag the component tag of assigned component
     */
    protected void addCustomComponentData(ComponentTag tag) {
        if (componentData != null) {
            for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
                CharSequence key = camelCaseToDash(entry.getKey());
                tag.append(Names.COMPONENT_CUSTOM_DATA_ATTRIBUTE_PREFIX + "-" + key,
                           entry.getValue().getObject(), Names.CLASS_NAME_SPLITTER);
            }
        }
    }

    private CharSequence camelCaseToDash(String key) {
        Args.notNull(key, "key");

        StringBuilder result = new StringBuilder(key.length() + 4);

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c == '-') {
                throw new IllegalArgumentException("Component data should not use '-' in its name: " + key);
            } else if (Character.isUpperCase(c)) {
                result.append('-').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result;
    }

    /**
     * Adds the required identifier attributes which are necessary to work with the flight manager
     *
     * @param component current assigned component
     * @param tag       the component tag of assigned component
     */
    protected void addRequiredComponentIdentifierAttributes(Component component, ComponentTag tag) {
        tag.put(Names.COMPONENT_DATA_ATTRIBUTE_SOURCE, getComponentSource(component));
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);

        tag.append("class", Names.JS_CLASS_NAME, Names.CLASS_NAME_SPLITTER);

        addRequiredComponentIdentifierAttributes(component, tag);
        addCustomComponentData(tag);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(WicketFlight.settings(component.getApplication()).wicketFlightJavaScriptResourceReference()));

        addComponentResourceReferences(component, response);
    }

    @Override
    public void detach(Component component) {
        super.detach(component);

        if (componentData != null) {
            for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
                entry.getValue().detach();
            }
        }
    }

    /**
     * Returns the name for the flight component
     *
     * @param component current assigned component
     * @return the flight component name
     */
    protected String getComponentSource(Component component) {
        return component.getClass().getSimpleName();
    }


    /**
     * Adds the css and javascript references for the flight component
     *
     * @param component current assigned component
     * @param response  current header response
     */
    protected abstract void addComponentResourceReferences(Component component, IHeaderResponse response);
}
