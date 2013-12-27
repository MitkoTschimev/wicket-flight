/*jslint browser:true, forin:true, nomen:true, plusplus:true, sloppy:true, maxlen:120, indent:4 */
/*global jQuery:false, defineComponent:true, RollingLabel:true */

/**
 * @author tfiwm
 *
 * This labels uses css3 to roll if you hover it
 */

RollingLabel = (function ($, defineComponent) {

    /**
     * @constructor
     */
    return defineComponent({
        name: "RollingLabel",
        mixins: [

            function () {

                /**
                 * @param {Event} event
                 */
                this.onHover = function(event) {
                    this.$node.toggleClass("rolling");
                }

                /**
                 * Initialize and bind events
                 */
                this.after("initialize", function () {
                    this.on("hover", this.onHover);
                });
            }
        ]
    });

}(jQuery, defineComponent));
