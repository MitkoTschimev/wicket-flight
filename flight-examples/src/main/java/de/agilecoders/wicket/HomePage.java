package de.agilecoders.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

        Label label = new org.apache.wicket.markup.html.basic.Label();

        label.add(new de.agilecoders.wicket.flight.FlightBehavior("TestComponent"))
        add(label);
		// TODO Add your page's components here

    }
}
