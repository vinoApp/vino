'use strict';

angular.module('vino.ui')
    .controller('DomainCtrl', function ($scope, $routeParams, Common, Domains, DomainBottles) {

        // Init model
        $scope.domain = {};
        $scope.bottles = [];

        // The domain id must be provided
        if ($routeParams['domainID']) {
            Domains.get({id: $routeParams['domainID']}, function (response) {
                $scope.domain = response.domain;

                DomainBottles.query({id: $scope.domain.id}, function (bottles) {
                    $scope.bottles = bottles;
                });
            });
        }
    });
