angular.module('vino.ui').controller("DomainsCtrl", function ($scope, $filter, Domain, Notification, ngProgress) {

    $scope.$watch("selectedAOC", function (aoc) {
        if (!aoc) {
            return;
        }
        loadData();
    });

    $scope.displayAll = function () {
        $scope.selectedAOC = null;
        loadData();
    };

    var loadData = function () {

        var aoc = $scope.selectedAOC;

        ngProgress.start();
        if (!aoc) {
            $scope.domains = Domain.query({}, function() {
                ngProgress.complete();
                ngProgress.stop();
            });
        } else {
            $scope.domains = Domain.query({aoc: aoc._id}, function(){
                ngProgress.complete();
                ngProgress.stop();
            });
        }
    };

    $scope.remove = function (domain) {

        Notification.dialog.confirm($filter('i18n')('action.delete.domain.confirm'), null,
            function (yes) {
                if (yes) {
                    Domain.remove({id: domain._id}, function () {
                        Notification.notify.success($filter('i18n')('domain.delete.success'));
                        loadData();
                    }, function () {
                        Notification.notify.error($filter('i18n')('domain.update.error'));
                    });
                }
            });
    };

    $scope.create = function (domain) {
        $scope.redirect('/domains/new');
    };

});