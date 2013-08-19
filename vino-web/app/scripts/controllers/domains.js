'use strict';

angular.module('vino.ui')
    .controller('DomainsCtrl', function ($scope, Domains) {

        $scope.domains = Domains.query();

        $scope.removeDomain = function (domainID) {
            // TODO
        };
    });
