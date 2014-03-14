angular.module('vino.ui').controller("DomainCtrl", function ($scope, $routeParams, Domain) {

    if (!$routeParams.id) {
        console.log("Domain id not provided.");
        return;
    }

    var domainId = $routeParams.id;

    $scope.domain = Domain.get({
        id: domainId
    });

    $scope.records = Domain.query({
        id: domainId,
        action: 'records'
    });

});