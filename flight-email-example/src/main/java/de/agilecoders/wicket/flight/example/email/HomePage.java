package de.agilecoders.wicket.flight.example.email;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.agilecoders.wicket.components.ui.ComposeBox;
import de.agilecoders.wicket.components.ui.MailItemsTable;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new MailItemsTable("mailItems"));
        add(new ComposeBox("composeBox"));
    }
}
