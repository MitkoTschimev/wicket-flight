package de.agilecoders.wicket.components.ui.folders;

import de.agilecoders.wicket.components.ui.composebox.ComposeBox;
import de.agilecoders.wicket.flight.FlightBehavior;
import de.agilecoders.wicket.requirejs.AmdModuleHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 *
 */
public class FoldersPanel extends Panel {

    public FoldersPanel(String id) {
        super(id);

        JavaScriptResourceReference reference = new JavaScriptResourceReference(FoldersPanel.class, "FoldersPanel.js");
        AmdModuleHeaderItem item = AmdModuleHeaderItem.forReference(reference, "FoldersPanel");

        add(FlightBehavior.newBuilder(this)
                    .withComponentSource("wicket!" + item.getName())
                    .withJsHeaderItem(item)
                    .build());
    }
}
