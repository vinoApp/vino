'use strict';

angular.module('vino.ui')
    .controller('DomainsCtrl', function ($scope, Domains) {

        $scope.domains = Domains.query();

        $scope.removeDomain = function (domain) {
            domain.$remove(function (response) {
                console.log(response);
                $scope.domains = Domains.query();
            });
        };
    });
