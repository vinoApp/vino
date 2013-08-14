'use strict';

angular.module('vino.ui')
    .controller('BottleCtrl', function ($scope, $routeParams, Bottles) {

        //
        var MODES = {
            VIEW: 1,
            EDIT: 2
        };

        // Init model
        $scope.bottle = {};

        // Set the mode
        var mode;
        if (!$routeParams['bottleID']) {
            mode = MODES.EDIT;
        } else {
            mode = MODES.VIEW;
            Bottles.getById({id: $routeParams['bottleID']}, function (response) {
                $scope.bottle = response.bottle;
                console.log($scope.bottle);
            });
        }


    });
