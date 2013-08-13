'use strict';

angular.module('vino.services').factory('Notification', function ($http) {

    return {

        info: function (text) {
            console.log(text);
        },
        error: function (text) {
            console.error(text);
        }
    };
});
