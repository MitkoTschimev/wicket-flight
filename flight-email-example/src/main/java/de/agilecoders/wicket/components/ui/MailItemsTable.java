package de.agilecoders.wicket.components.ui;

import de.agilecoders.wicket.flight.FlightBehavior;
import de.agilecoders.wicket.requirejs.AmdJavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 *
 */
public class MailItemsTable extends Panel {

    public MailItemsTable(String id) {
        super(id);

        JavaScriptResourceReference reference = new JavaScriptResourceReference(MailItemsTable.class, "mail_items.js");
        AmdJavaScriptHeaderItem item = AmdJavaScriptHeaderItem.forReference(reference, "mail_items");

        add(FlightBehavior.newBuilder(this)
                    .withJsHeaderItem(item)
                    .build());
    }
}
