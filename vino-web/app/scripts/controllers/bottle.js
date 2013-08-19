'use strict';

angular.module('vino.ui')
    .controller('BottleCtrl', function ($scope, $routeParams, Common, Bottles) {

        // Init model
        $scope.bottle = {};

        // Set the mode
        if (!$routeParams['bottleID']) {
            $scope.mode = Common.EDITOR_MODES.VIEW;
        } else {
            $scope.mode = Common.EDITOR_MODES.EDIT;
            Bottles.getById({id: $routeParams['bottleID']}, function (response) {
                $scope.bottle = response.bottle;
            });
        }
    });
