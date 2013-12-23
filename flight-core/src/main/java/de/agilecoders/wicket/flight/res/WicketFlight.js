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

    /*
     * A mixin to add namespace functionality
     */
    function namespaceMixin() {
        /**
         * Returns an event name namespaced to the component.
         *
         * E.g. "click" -> "click.componentX"
         *
         * @param {String} eventName
         * @return {String} The namespaced event name
         */
        this.getNameSpacedEventName = function (eventName) {
            return eventName + "." + this.name;
        };
    }

    /**
     * Wrapper to define a component and add all logic for integration
     * with Wicket.
     *
     * @param options.name {string} The components name as used in its
     *          data-component attribute
     * @param options.mixins {Array} An array of mixin functions as used in
     *          flight
     * @param options.parent {FlightComponent} optional A component to
     *          inherit from.
     * @return {FlightComponent} A new Flight component
     */
    function defineComponent(options) {
        var mixins = options.mixins,
            name = options.name,
            parent = options.parent,
            parentMixins, component;

        /**
         * Add the component name to the component instances.
         */
        function nameMixin() {
            this.name = name;
        }

        // 'Be liberal in what you accept', Postel's law
        // If mixins is not an array but a function, the caller most likely
        // forgot to wrap the mixin in an array.
        if (typeof mixins === "function") {
            mixins = [mixins];
        }

        if (parent) {
            // If there is a parent component to inherit from, extract all
            // its mixins (minus the base mixins from flight itself) and
            // concatenate the mixins for this new component.
            // This is the critical part, because it relies on flight's
            // internal implementation. However, there is no other way. At
            // least we have this part at a single location and not spread
            // amongst all components.
            parentMixins = parent.prototype.mixedIn.slice(3);

            mixins = parentMixins.concat(mixins);
        } else {
            // If there is no parent, we need to add the teardown mixin
            mixins.unshift(teardownMixin);
            // If there is no parent, we need to add the namespace mixin
            mixins.unshift(namespaceMixin);
        }

        // To set the right component name, the name mixin must come last. This
        // is because the parent has its own nameMixin, which we need to
        // overwrite. Of course we could remove the parent's nameMixin, still
        // this would have to come last.
        mixins.push(nameMixin);

        component = flight.component.apply(null, mixins);

        if (name) {
            // Register the component at the WicketFlightManager
            WicketFlightManager.registerComponent(name, component);
        }

        return component;
    }

    return defineComponent;

}(WicketFlightManager, flight));
