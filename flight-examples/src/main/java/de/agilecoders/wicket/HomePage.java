package de.agilecoders.wicket;

import de.agilecoders.wicket.components.ui.label.rollinglabel.GlowingLabel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new GlowingLabel("rollingLabel", "Hover me :)"));
    }
}
