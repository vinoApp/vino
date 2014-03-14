angular.module('vino.ui').controller("CellarCtrl", function ($scope, $routeParams, Cellar, Notification) {

    var loadData = function () {
        $scope.records = Cellar.query();
    };

    loadData();

    $scope.in = function (record, qty) {
        Cellar.in(record, qty, {
            success: function () {
                Notification.notify.success('In : success');
                loadData();
            },
            error: function () {
                Notification.notify.error('In : error');
            }
        });
    };

    $scope.out = function (record, qty) {
        Cellar.out(record, qty, {
            success: function () {
                Notification.notify.success('Out : success');
                loadData();
            },
            error: function () {
                Notification.notify.error('Out : error');
            }
        });
    };
});