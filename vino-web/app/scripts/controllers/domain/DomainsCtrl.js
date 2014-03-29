angular.module('vino.ui').controller("DomainsCtrl", function ($scope, Domain, Notification) {

    $scope.$watch("selectedAOC", function (aoc) {
        if (!aoc) {
            return;
        }
        $scope.domains = Domain.query({aoc: aoc._id});
    });

    $scope.displayAll = function () {
        $scope.domains = Domain.query();
    };

    $scope.remove = function (domain) {

        Notification.dialog.confirm('Would you really want to remove this domain ?', null,
            function (yes) {
                if (yes) {
                    Domain.remove({id: domain._id}, function () {
                        Notification.notify.success("Domain successfuly removed.");
                        $scope.domains = Domain.query();
                    }, function () {
                        Notification.notify.error("Error during deletion.");
                    });
                }
            });
    };

    $scope.create = function (domain) {
        $scope.redirect('/domains/new');
    };

});