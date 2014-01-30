/*jslint browser:true, forin:true, nomen:true, plusplus:true, sloppy:true, maxlen:120, indent:4 */
/*global Wicket:false, jQuery:false, WicketFlightManager:true */

/* requires component markup <div class="js-fc" data-fc="ComponentX"></div>
 * requires registration WicketFlightManager.registerComponent("ComponentX", ComponentX);
 */

WicketFlightManager = (function (Wicket, $) {
    var components = {},

        /**
         * The selector to indicate which node is a flight component
         * @type {string}
         */
        COMPONENT_SELECTOR = ".js-fc",

        /**
         * The value of the component name / requirejs path data attribute
         * @type {string}
         */
        COMPONENT_NAME_ATTR = "fc",

        /**
         * The string length of the component name data attribute
         * @type {number}
         */
        COMPONENT_NAME_ATTR_LENGTH = COMPONENT_NAME_ATTR.length;

    /*
     * Finds all elements that denote a component.
     */
    function getComponentElements(element) {
        var root = $(element),
            elements = root.find(COMPONENT_SELECTOR).add(root.filter(COMPONENT_SELECTOR));

        return elements;
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

            if (key.indexOf(COMPONENT_NAME_ATTR) === 0 && key.length > COMPONENT_NAME_ATTR_LENGTH) {
                // if key starts with "fc" and does not equal "fc"

                // omit "COMPONENT_NAME_ATTR" value and change first char to lower case
                property = key.charAt(COMPONENT_NAME_ATTR_LENGTH).toLowerCase() +
                           key.substring(COMPONENT_NAME_ATTR_LENGTH + 1);

                componentAttributes[property] = options[key];
            }
        }

        return componentAttributes;
    }

    /**
     * Attach a single element to its component. The element is passed in
     * via 'this'.
     */
    function attachComponentElement() {
        var name = this.getAttribute("data-fc"),
            component;

        if(typeof requirejs == "undefined") {
            component = window[name];
        } else {
            component = require(name);
        }

        if (component) {
            component.attachTo(this, getDataComponentAttributes(this));
        } else {
            throw new Error("Component can't be attached", name);
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

    return {
        "registerComponent": function (name, component) {
            components[name] = component;
        },
        "getComponent": function (name) {
            return components[name] || null;
        }
    };
}(Wicket, jQuery));
