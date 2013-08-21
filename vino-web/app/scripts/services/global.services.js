'use strict';


angular.module('vino.services').factory('Common', function () {

    return {
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

