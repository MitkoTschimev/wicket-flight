package de.agilecoders.wicket.components.ui;

import de.agilecoders.wicket.flight.FlightBehavior;
import de.agilecoders.wicket.requirejs.AmdModuleHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 *
 */
public class ComposeBox extends Panel {

    public ComposeBox(String id) {
        super(id);

        JavaScriptResourceReference reference = new JavaScriptResourceReference(ComposeBox.class, "ComposeBox.js");
        AmdModuleHeaderItem item = AmdModuleHeaderItem.forReference(reference, "compose_box");

        add(FlightBehavior.newBuilder(this)
                .withComponentSource("wicket!" + item.getName())
                    .withJsHeaderItem(item)
                    .build());
    }
}
