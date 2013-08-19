'use strict';


angular.module('vino.services').factory('Common', function () {

    return {

        // Editor modes
        EDITOR_MODES: {
            VIEW: 1,
            EDIT: 2,
            CREATE: 3
        }
    };

});

angular.module('vino.services').factory('Notification', function () {

    return {

        info: function (text) {
            console.log(text);
        },
        error: function (text) {
            console.error(text);
        }
    };
});

