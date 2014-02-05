package de.agilecoders.wicket.components.ui.label.rollinglabel;

import org.apache.wicket.markup.html.basic.Label;

import de.agilecoders.wicket.flight.FlightBehavior;

/**
 * @author tfiwm
 */
public class GlowingLabel extends Label {

    public GlowingLabel(String id, String label) {
        super(id, label);

        add(FlightBehavior.newBuilder(this)
                    .withComponentSource("wicket!label")
                    .withCssResourceReference()
                    .withJsHeaderItem()
                    .build());
    }
}
