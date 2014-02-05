/*jslint browser:true, forin:true, nomen:true, plusplus:true, sloppy:true, maxlen:120, indent:4 */
/*global Wicket:false, jQuery:false, WicketFlight:true */

/**
 * requires component markup
 *
 * with flight standalone:
 *      <div class="js-fc" data-fcs="GlobalComponentFunction"></div>
 *
 * with requirejs:
 *      <div class="js-fc" data-fcs="path/to/component/file"></div>
 */

WicketFlight = (function (Wicket, $) {
    var components = {},

        /**
         * The selector to indicate which node is a flight component
         * @type {string}
         */
        COMPONENT_SELECTOR = ".js-fc",

        /**
         * The value of the flight component source  data attribute
         * @type {string}
         */
        COMPONENT_SOURCE_ATTR = "fcs",

        /**
         * Prefix for the flight component custom data
         *
         * @type {string}
         */
        COMPONENT_CUSTOM_DATA_PREFIX = "fc",

        /**
         * The string length of the component custom data prefix data attribute
         * @type {number}
         */
        COMPONENT_CUSTOM_DATA_PREFIX_LENGTH = COMPONENT_CUSTOM_DATA_PREFIX.length,

        /**
         * TODO: the right path for the compose? how can i combine it with requirejs....
         *
         * @type {exports}
         */
        defineComponent = withRequireJs() ? require("flight/lib/component") : flight.component;

    function withRequireJs() {
        return window.requirejs !== undefined;
    }

    /*
     * Finds all elements that denote a component.
     */
    function getComponentElements(element) {
        return $(element).find(COMPONENT_SELECTOR).addBack(COMPONENT_SELECTOR);
    }

    /**
     * This function extracts the custom component properties from the backend to the flight component
     *
     * The attributes must start with data-fc-MY_PROPERTY
     *
     * @example
     * <div class="js-fc" data-fc="mycomponent" data-fc-customproperty="500">
     *     Component content
     * </div>
     *
     * The flight component contains now the attribute customproperty with the value 500
     * You can access this one with
     * this.attr.customproperty
     *
     * @example
     * <div class="js-fc" data-fc="mycomp2" data-fc-custom-property-with-minus="500">
     *     Component content
     * </div>
     *
     * In this case the flight component contains the property with camelcase.
     * You can access this one with
     * this.attr.customPropertyWithMinus
     *
     * @param {Element} node
     * @returns {Object}
     */
    function getDataComponentAttributes(node) {
        var options = $(node).data(),
            key, property, componentAttributes = {};

        for (key in options) {
            // jQuery's data() method includes data-* attributes, changing the
            // name from hyphenation to camel case, omitting the "data" part.
            // e.g. data-fc-foo-bar -> fcFooBar
            // We want to go one step further and remove the "fc" part as
            // well.
            // e.g. fcFooBar -> fooBar

            if (isCustomDataAttribute(key)) {
                // omit "COMPONENT_NAME_ATTR" value and change first char to lower case
                property = key.charAt(COMPONENT_CUSTOM_DATA_PREFIX_LENGTH).toLowerCase() +
                           key.substring(COMPONENT_CUSTOM_DATA_PREFIX_LENGTH + 1);

                componentAttributes[property] = options[key];
            }
        }

        return componentAttributes;
    }

    /**
     * Returns true if the key is an custom data attribute for flight components
     *
     * @param {string} key
     * @returns {boolean}
     */
    function isCustomDataAttribute(key) {
        return key.indexOf(COMPONENT_CUSTOM_DATA_PREFIX) === 0 && key.indexOf(COMPONENT_SOURCE_ATTR) === -1;
    }

    /**
     * Teardown mixin to call teardown for the special component over an event.
     * Otherwise we can't delete the special component completely with the connected bindings
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
     * Creates the component with the wicketflight core mixins
     *
     * @param {function} component
     * @returns {FlightComponent}
     */
    function createComponentWithWicketFlightCoreMixins(component) {
        return defineComponent(component, teardownMixin);
    }

    /**
     * Attach a single element to its component. The element is passed in
     * via 'this'.
     */
    function attachComponentElement() {
        var source = this.getAttribute("data-" + COMPONENT_SOURCE_ATTR),
            component;

        if(withRequireJs()) {
            component = require(source);
        } else {
            component = window[source];
        }

        if (component) {
            component = createComponentWithWicketFlightCoreMixins(component);
            component.attachTo(this, getDataComponentAttributes(this));
        } else {
            throw new Error("Component can't be attached", source);
        }
    }

    /**
     * Attach all elements within an element (including the element itself)
     * to their components.
     */
    function attachAllComponentElements(element) {
        var componentElements = getComponentElements(element);

        componentElements.each(attachComponentElement);
    }

    /**
     * Listen to Wicket removing nodes. Trigger teardown on all component instances
     *
     * @param {Event} jqEvent
     * @param {Element} element
     */
    function removeNode(jqEvent, element) {
        var componentElements = getComponentElements(element);

        componentElements.trigger("teardown");
    }

    //Subscribe removeNode function to event /dom/node/removing
    Wicket.Event.subscribe("/dom/node/removing", removeNode);


    /**
     * Listen to Wicket adding nodes. Attach all component nodes to their components
     *
     * @param {Event} jqEvent
     * @param {Element} element
     */
    function addNode(jqEvent, element) {
        attachAllComponentElements(element);
    }

    //Subscribe addNode function to event /dom/node/removing
    Wicket.Event.subscribe("/dom/node/added", addNode);

    //Wicket.Ajax.registerPostCallHandler(function () {
    // This could be used to improve the performance
    // when several elements were replaced at once
    // We would cache these elements and call the handlers
    // once. Of course, this would only work for added
    // elements, not for elements about to be removed,
    // since it's too late here for that.
    //});

    // Attach all component elements to their components on startup
    $(function () {
        attachAllComponentElements(document.body);
    });

    // Detach all component elements from their instances and remove
    // the instances on unload
    $(window).on("unload", function () {
        var name;

        for (name in components) {
            components[name].teardownAll();
        }
    });
}(Wicket, jQuery));
