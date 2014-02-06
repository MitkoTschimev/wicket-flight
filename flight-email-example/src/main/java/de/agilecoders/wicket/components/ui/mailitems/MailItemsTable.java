package de.agilecoders.wicket.components.ui.mailitems;

import de.agilecoders.wicket.flight.FlightBehavior;
import de.agilecoders.wicket.requirejs.AmdModuleHeaderItem;
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

        JavaScriptResourceReference reference = new JavaScriptResourceReference(MailItemsTable.class, "MailItems.js");
        AmdModuleHeaderItem item = AmdModuleHeaderItem.forReference(reference, "MailItems");

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
