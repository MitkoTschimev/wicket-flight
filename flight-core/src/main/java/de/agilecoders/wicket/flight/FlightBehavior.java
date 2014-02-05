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
public abstract class FlightBehavior extends AbstractFlightBehavior {

    public static Builder newBuilder(Component component) {
        return new Builder(Args.notNull(component, "component"));
    }

    private final String flightComponentSource;

    /**
     * Construct, uses an empty custom component data map.
     *
     * @param flightComponentSource the name of the flight component
     */
    public FlightBehavior(String flightComponentSource) {
        this(flightComponentSource, null);
    }

    /**
     * Construct.
     *
     * @param flightComponentSource the name of the flight component
     * @param componentData       map of models which are containing data for the flight component default Attributes
     */
    public FlightBehavior(String flightComponentSource, Map<String, IModel<String>> componentData) {
        super(componentData);

        this.flightComponentSource = flightComponentSource;
    }

    @Override
    protected String getComponentSource(Component component) {
        return flightComponentSource;
    }

    public static final class Builder {

        private final Component component;
        private String componentSource = null;
        private CssResourceReference cssResourceReference = null;
        private JavaScriptResourceReference jsResourceReference = null;
        private Map<String, IModel<String>> componentData = null;

        private Builder(Component component) {
            this.component = component;
            this.componentSource = component.getClass().getSimpleName();
        }

        public Builder withComponentSource(final String source) {
            this.componentSource = source;
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
            this.cssResourceReference = new CssResourceReference(component.getClass(), componentSource + ".css");
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
            this.jsResourceReference = new JavaScriptResourceReference(component.getClass(), componentSource + ".js");
            return this;
        }

        public FlightBehavior build() {
            return new FlightBehavior(componentSource, componentData) {
                @Override
                protected String getComponentSource(Component component) {
                    return componentSource;
                }

                @Override
                protected void addComponentResourceReferences(Component component, IHeaderResponse response) {

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
