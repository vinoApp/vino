'use strict';

angular.module('vino.ui')
    .controller('DomainEditorCtrl', function ($scope, $routeParams, Common, Domains, Origins) {

        // Init model
        $scope.domain = {};
        $scope.origins = [];

        // Retrieve the domain (editor mode)
        if ($routeParams['domainID']) {
            Domains.get({id: $routeParams['domainID']}, function (response) {
                $scope.domain = response.data;
            });
            $scope.origins = Origins.getAll();
        }
    });
