package de.agilecoders.wicket;

import de.agilecoders.wicket.flight.FlightBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        Label label = new Label("behaviorTest");
        label.add(new FlightBehavior("TestComponent"));

        add(label);
    }
}
