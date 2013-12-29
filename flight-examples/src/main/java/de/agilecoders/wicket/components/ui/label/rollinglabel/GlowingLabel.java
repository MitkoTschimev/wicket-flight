package de.agilecoders.wicket.components.ui.label.rollinglabel;

import de.agilecoders.wicket.flight.FlightBehavior;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author tfiwm
 */
public class GlowingLabel extends Label {


    public GlowingLabel(String id, String label) {
        super(id, label);

        add(FlightBehavior.newBuilder(this)
                    .withCssResourceReference()
                    .withJsResourceReference()
                    .build());
    }
}
