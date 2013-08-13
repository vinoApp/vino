'use strict';

angular.module('vino.ui')
    .controller('CellarCtrl', function ($scope, Cellar) {

        $scope.records = Cellar.query();

    });
