angular.module('vino.ui').controller("CellarCtrl", function ($scope, $routeParams, $filter, Common, Cellar, Notification) {

    var loadData = function () {
        $scope.records = Cellar.query();
    };

    $scope.$on(Common.events.cellar.update, function () {
        loadData();
    });

    angular.extend($scope, {

        edit: function (record) {
            $scope.recordToEdit = record;
        },

        in: function (record, qty) {
            Cellar.in(record, qty, {
                success: function () {
                    Notification.notify.success($filter('i18n')('cellar.in.success'));
                    loadData();
                },
                error: function () {
                    Notification.notify.error($filter('i18n')('cellar.in.error'));
                }
            });
        },

        out: function (record, qty) {
            Cellar.out(record, qty, {
                success: function () {
                    Notification.notify.success($filter('i18n')('cellar.out.success'));
                    loadData();
                },
                error: function () {
                    Notification.notify.error($filter('i18n')('cellar.out.error'));
                }
            });
        }
    });

    loadData();

});