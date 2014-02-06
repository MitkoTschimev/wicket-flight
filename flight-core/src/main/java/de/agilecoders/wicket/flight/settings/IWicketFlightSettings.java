package de.agilecoders.wicket.flight.settings;

import org.apache.wicket.request.resource.ResourceReference;

/**
 * wicket flight settings.
 *
 * @author miha
 */
public interface IWicketFlightSettings {

    /**
     * @return the flight javascript resource reference
     */
    ResourceReference flightJavaScriptResourceReference();

    /**
     * @return the wicket-flight javascript resource reference
     */
    ResourceReference wicketFlightJavaScriptResourceReference();

    /**
     * @return the wicket-flight manager javascript resource reference
     */
    ResourceReference wicketFlightManagerJavaScriptResourceReference();

}
