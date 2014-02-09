angular.module('vino.ui').controller("DomainsCtrl", function ($scope, Domain) {

    $scope.domains = Domain.query();

});