'use strict';

angular.module('vino.ui')
    .controller('PendingsCtrl', function ($scope, Bottles, Pendings) {

        $scope.pendings = Pendings.query();

    });
