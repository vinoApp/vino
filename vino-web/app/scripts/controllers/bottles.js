'use strict';

angular.module('vino.ui')
    .controller('BottlesCtrl', function ($scope, Bottles) {
        $scope.bottles = Bottles.query();
    });
