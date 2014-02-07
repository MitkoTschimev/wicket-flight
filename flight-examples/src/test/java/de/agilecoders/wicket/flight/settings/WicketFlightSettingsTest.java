package de.agilecoders.wicket.flight.settings;

import de.agilecoders.wicket.flight.WicketFlight;
import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the settings class
 *
 * @author miha
 */
public class WicketFlightSettingsTest {

    private WicketTester tester;

    @Before
    public void setUp() throws Exception {
        tester = new WicketTester(new MockApplication() {
            @Override
            protected void init() {
                super.init();

                WicketWebjars.install(this);
                WicketFlight.install(this);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        if (tester != null) tester.destroy();
    }

    @Test
    public void webjarsFlightResourceIsAvailable() throws Exception {
        tester.startResourceReference(WicketFlight.settings().flightJavaScriptResourceReference());

        assertThat(tester.getLastResponseAsString(), startsWith("/*! Flight v"));
    }
}
