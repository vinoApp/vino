angular.module('vino.ui').controller("CellarCtrl", function ($scope, $routeParams, Common, Cellar, Notification) {

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
                    Notification.notify.success('In : success');
                    loadData();
                },
                error: function () {
                    Notification.notify.error('In : error');
                }
            });
        },

        out: function (record, qty) {
            Cellar.out(record, qty, {
                success: function () {
                    Notification.notify.success('Out : success');
                    loadData();
                },
                error: function () {
                    Notification.notify.error('Out : error');
                }
            });
        }
    });

    loadData();

});