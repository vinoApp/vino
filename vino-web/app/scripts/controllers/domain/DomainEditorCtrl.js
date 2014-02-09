angular.module('vino.ui').controller("DomainEditorCtrl", function ($scope, $routeParams, Domain) {

    // Provided domain ID
    var domainId = $routeParams.id;

    // Edit mode
    if ($routeParams.id) {
        $scope.domain = Domain.get({
            id: domainId
        });
    }
    // Creation mode
    else {
        $scope.domain = new Domain();
    }

    $scope.edit = function (domain) {

    };
});