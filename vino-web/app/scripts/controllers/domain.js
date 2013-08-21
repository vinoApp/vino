'use strict';

angular.module('vino.ui')
    .controller('DomainCtrl', function ($scope, $routeParams, Common, Domains, DomainBottles) {

        // Init model
        $scope.domain = {};
        $scope.bottles = [];

        // Set the mode
        if ($routeParams['mode'] == "view") {
            $scope.mode = Common.EDITOR_MODES.VIEW;
        }
        else if ($routeParams['mode'] == "edit") {
            $scope.mode = Common.EDITOR_MODES.EDIT;
        }
        else {
            $scope.mode = Common.EDITOR_MODES.CREATE;
        }

        if ($routeParams['domainID']) {
            Domains.get({id: $routeParams['domainID']}, function (response) {
                $scope.domain = response.domain;

                DomainBottles.query({id: $scope.domain.id}, function (bottles) {
                    $scope.bottles = bottles;
                });
            });
        }
    });
