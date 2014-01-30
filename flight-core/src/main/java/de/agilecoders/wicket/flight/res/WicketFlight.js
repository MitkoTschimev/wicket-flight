/*jslint browser:true, forin:true, nomen:true, plusplus:true, sloppy:true, maxlen:120, indent:4 */
/*global WicketFlightManager:false, flight:false, defineComponent:true */

defineComponent = (function (WicketFlightManager, flight) {

    /**
     * A mixin to expose teardown behavior to the DOM node.
     *
     * A component instance can be torn down by calling
     *     $(node).trigger("teardown");
     *
     * The teardown function can bubble up if true is given as second parameter
     *     $(node).trigger("teardown", true);
     */
    function teardownMixin() {
        this.after("initialize", function () {
            this.on("teardown",
                /**
                 * @param {Event} event
                 * @param {boolean} bubbleUp Teardown should bubble up
                 */
                function (event, bubbleUp) {
                    this.teardown();
                    if(!bubbleUp) {
                        event.stopPropagation();
                    }
                }
            );
        });
    }

    /**
     * Add our wicket flight custom core mixins
     *
     * @param mixins
     */
    function addWicketFlightCoreMixins(mixins) {
        mixins.unshift(teardownMixin);
    }

    /**
     * Creates a new flight component with the registered mixins
     *
     * @param mixins
     * @returns {FlightComponent}
     */
    function createFlightComponent(mixins) {
        return flight.component.apply(null, mixins);
    }

    /**
     * Wrapper to define a component and add all logic for integration
     * with Wicket.
     *
     * @param options.name {string} The components name as used in its data-fc attribute
     * @param options.mixins {Array} An array of mixin functions as used in flight
     * @return {FlightComponent} A new Flight component
     */
    function defineComponent(options) {
        var mixins = options.mixins,
            name = options.name,
            component;

        // 'Be liberal in what you accept', Postel's law
        // If mixins is not an array but a function, the caller most likely
        // forgot to wrap the mixin in an array.
        if (typeof mixins === "function") {
            mixins = [mixins];
        }

        addWicketFlightCoreMixins(mixins);
        component = createFlightComponent(mixins);

        if (name) {
            // Register the component at the WicketFlightManager
            WicketFlightManager.registerComponent(name, component);
        }

        return component;
    }

    return defineComponent;

}(WicketFlightManager, flight));
