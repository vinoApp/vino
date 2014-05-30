angular.module('vino.ui')
    .controller('CellarCtrl', function ($scope, $routeParams, $filter, Common, Cellar, CellarContent, Notification) {

        var loadCellarContent = function () {
            $scope.records = CellarContent.query({id: $scope.cellar._id});
        };

        $scope.$on(Common.events.cellar.update, function () {
            loadCellarContent();
        });

        angular.extend($scope, {

            add: function () {
                $scope.recordToEdit = { cellar: $scope.cellar };
            },

            edit: function (record) {
                $scope.recordToEdit = record;
            },

            in: function (record, qty) {
                CellarContent.in($scope.cellar._id, record, qty, {
                    success: function () {
                        Notification.notify.success($filter('i18n')('cellar.in.success'));
                        loadCellarContent();
                    },
                    error: function () {
                        Notification.notify.error($filter('i18n')('cellar.in.error'));
                    }
                });
            },

            out: function (record, qty) {
                CellarContent.out($scope.cellar._id, record, qty, {
                    success: function () {
                        Notification.notify.success($filter('i18n')('cellar.out.success'));
                        loadCellarContent();
                    },
                    error: function () {
                        Notification.notify.error($filter('i18n')('cellar.out.error'));
                    }
                });
            }
        });

        // Watch selected cellar changes
        $scope.$watch("cellar", function (cellar) {
            if (!cellar) {
                return;
            }
            loadCellarContent();
        });
    });