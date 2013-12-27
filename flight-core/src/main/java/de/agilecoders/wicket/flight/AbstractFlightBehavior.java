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
 * @author mtschimev
 */
public abstract class AbstractFlightBehavior extends Behavior {

    private final Map<String, IModel<String>> componentData;

    /**
     * Construct, uses an empty custom component data map.
     */
    public AbstractFlightBehavior() {
        this(Collections.<String, IModel<String>>emptyMap());
    }

    /**
     * Construct.
     *
     * @param componentData map of models which are containing data for the flight component default Attributes
     */
    public AbstractFlightBehavior(Map<String, IModel<String>> componentData) {
        this.componentData = componentData;
    }

    /**
     * Converts the map to html data attributes for the flight component
     *
     * @param tag
     */
    protected void addCustomComponentData(ComponentTag tag) {
        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            tag.append("data-component-" + entry.getKey(), entry.getValue().getObject(), " ");
        }
    }

    /**
     * Adds the required identifier attributes which are necessary to work with the flight manager
     *
     * @param component
     * @param tag
     */
    private void addRequiredComponentIdentifierAttributes(Component component, ComponentTag tag) {
        String componentName = getComponentName(component);

        if (component.getApplication().usesDevelopmentConfig()) {
            WicketBehaviorUtils.assertCamelCase(componentName);
        }

        tag.put("data-component", componentName);
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);

        tag.append("class", "js-flight-component", " ");

        addRequiredComponentIdentifierAttributes(component, tag);
        addCustomComponentData(tag);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(component.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(component.getApplication().getJavaScriptLibrarySettings().getWicketEventReference()));
        response.render(JavaScriptHeaderItem.forReference(WicketFlightJavascriptReference.instance()));
        addComponentResourceReferences(component, response);
    }

    @Override
    public void detach(Component component) {
        super.detach(component);

        for (Map.Entry<String, IModel<String>> entry : componentData.entrySet()) {
            entry.getValue().detach();
        }
    }

    /**
     * Returns the name for the flight component
     *
     * @param component
     * @return
     */
    protected abstract String getComponentName(Component component);


    /**
     * Adds the css and javascript references for the flight component
     *
     * @param component
     * @param response
     */
    protected abstract void addComponentResourceReferences(Component component, IHeaderResponse response);
}
