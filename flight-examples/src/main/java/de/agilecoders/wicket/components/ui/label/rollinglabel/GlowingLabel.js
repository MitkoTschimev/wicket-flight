/*jslint browser:true, forin:true, nomen:true, plusplus:true, sloppy:true, maxlen:120, indent:4 */
/*global jQuery:false, defineComponent:true, GlowingLabel:true */

/**
 * @author tfiwm
 *
 * This labels uses css3 to roll if you hover it
 */

GlowingLabel = (function ($, flight) {

    function GlowingLabel () {

        /**
         * @param {Event} event
         */
        this.onHover = function(event) {
            this.$node.toggleClass("glow");
        };

        /**
         * Initialize and bind events
         */
        this.after("initialize", function () {
            this.on("mouseover", this.onHover);
            this.on("mouseout", this.onHover);
        });
    };

    return flight.component(GlowingLabel);

}(jQuery, flight));
