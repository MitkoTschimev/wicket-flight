package de.agilecoders.wicket.components.ui;

import de.agilecoders.wicket.flight.FlightBehavior;
import de.agilecoders.wicket.requirejs.AmdJavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MailItemsTable extends Panel {

    public MailItemsTable(String id) {
        super(id);

        JavaScriptResourceReference reference = new JavaScriptResourceReference(MailItemsTable.class, "mail_items.js");
        AmdJavaScriptHeaderItem item = AmdJavaScriptHeaderItem.forReference(reference, "mail_items");

        Map<String, IModel<String>> map = new HashMap<>();
        map.put("itemContainerSelector", Model.of("#mail_items_TB"));
        map.put("selectedFolders", Model.of("['inbox']"));

        add(FlightBehavior.newBuilder(this)
                    .withComponentSource("wicket!" + item.getName())
                    .withJsHeaderItem(item)
                    .withComponentData(map)
                    .build());
    }
}
