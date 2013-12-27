package de.agilecoders.wicket.components.ui.label.rollinglabel;

import de.agilecoders.wicket.flight.SimpleFlightBehavior;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author tfiwm
 */
public class GlowingLabel extends Label {


    public GlowingLabel(String id, String label) {
        super(id, label);
        add(new SimpleFlightBehavior());
    }
}
