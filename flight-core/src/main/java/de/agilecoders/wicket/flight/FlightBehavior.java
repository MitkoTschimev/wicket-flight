package de.agilecoders.wicket.flight;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.lang.Args;

import java.util.Map;

/**
 * A behaviour to connect wicket components with flight components.
 */
public class FlightBehavior extends AbstractFlightBehavior {

    public static Builder newBuilder(Component component) {
        return new Builder(Args.notNull(component, "component"));
    }

    private final String flightComponentName;

    /**
     * Construct, uses an empty custom component data map.
     *
     * @param flightComponentName the name of the flight component
     */
    public FlightBehavior(String flightComponentName) {
        this(flightComponentName, null);
    }

    /**
     * Construct.
     *
     * @param flightComponentName the name of the flight component
     * @param componentData       map of models which are containing data for the flight component default Attributes
     */
    public FlightBehavior(String flightComponentName, Map<String, IModel<String>> componentData) {
        super(componentData);

        this.flightComponentName = flightComponentName;
    }

    @Override
    protected String getComponentName(Component component) {
        return this.flightComponentName;
    }

    public static final class Builder {

        private final Component component;
        private String componentName = null;
        private CssResourceReference cssResourceReference = null;
        private JavaScriptResourceReference jsResourceReference = null;
        private Map<String, IModel<String>> componentData = null;

        private Builder(Component component) {
            this.component = component;
            this.componentName = component.getClass().getSimpleName();
        }

        public Builder withComponentName(final String name) {
            this.componentName = name;
            return this;
        }

        public Builder withComponentData(final Map<String, IModel<String>> componentData) {
            this.componentData = componentData;
            return this;
        }

        public Builder withCssResourceReference(final CssResourceReference reference) {
            this.cssResourceReference = reference;
            return this;
        }

        /**
         * The css resource is loaded with the same class name like the wicket component
         * from the same package hierarchy
         *
         * @return
         */
        public Builder withCssResourceReference() {
            this.cssResourceReference = new CssResourceReference(component.getClass(), componentName + ".css");
            return this;
        }

        public Builder withJsResourceReference(final JavaScriptResourceReference reference) {
            this.jsResourceReference = reference;
            return this;
        }

        /**
         * The javascript resource is loaded with the same class name like the wicket component
         * from the same package hierarchy
         *
         * @return
         */
        public Builder withJsResourceReference() {
            this.jsResourceReference = new JavaScriptResourceReference(component.getClass(), componentName + ".js");
            return this;
        }

        public FlightBehavior build() {
            return new FlightBehavior(componentName, componentData) {
                @Override
                protected void addComponentResourceReferences(Component component, IHeaderResponse response) {
                    super.addComponentResourceReferences(component, response);

                    if (cssResourceReference != null) {
                        response.render(CssHeaderItem.forReference(cssResourceReference));
                    }

                    if (jsResourceReference != null) {
                        response.render(JavaScriptHeaderItem.forReference(jsResourceReference));
                    }
                }
            };
        }
    }
}
