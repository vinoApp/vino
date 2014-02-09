angular.module('vino.ui').controller("GlobalCtrl", function ($scope, $location) {

    $scope.redirect = function (url) {
        $location.path(url);
    };

});