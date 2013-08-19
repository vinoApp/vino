'use strict';

angular.module('vino.ui')
    .controller('DomainCtrl', function ($scope, $routeParams, Common, Domains) {

        // Init model
        $scope.domain = {};

        // Set the mode
        if ($routeParams['mode'] == "new") {
            $scope.mode = Common.EDITOR_MODES.CREATE;
        }
        else if ($routeParams['mode'] == "view" || $routeParams['mode'] == "edit") {

            if ($routeParams['mode'] == "view") {
                $scope.mode = Common.EDITOR_MODES.VIEW;
            }
            if ($routeParams['mode'] == "edit") {
                $scope.mode = Common.EDITOR_MODES.EDIT;
            }
            if (!$routeParams['domainID']) {
                return;
            }

            Domains.get({id: $routeParams['domainID']}, function (response) {
                $scope.domain = response.domain;
            });
        }
        else {
            // Nothing to do
        }

    });
