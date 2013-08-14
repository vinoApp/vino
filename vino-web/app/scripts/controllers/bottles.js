'use strict';

angular.module('vino.ui')
    .controller('BottlesCtrl', function ($scope, Bottles, Pendings) {

        $scope.bottles = Bottles.query();

        $scope.pendings = Pendings.query();

    });
