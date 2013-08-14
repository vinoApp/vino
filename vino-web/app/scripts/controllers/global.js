'use strict';

angular.module('vino.ui')
    .controller('GlobalCtrl', function ($scope, $location) {

        $scope.redirect = function (path) {
            $location.path(path);
        };

    });
