angular.module("vino.ui").directive("recordManager", function (Common, Cellar, Notification) {

    return {
        restrict: 'E',
        scope: {
            modalId: '@'
        },
        templateUrl: 'partials/record.edit.tmpl.html',
        link: function ($scope, elt) {
            $scope.close = function () {
                $(elt).modal('hide');
            };
        },
        controller: function ($scope, $rootScope) {

            $scope.record = {
                code: {
                    value: ''
                }
            };

            $scope.save = function (record) {
                record.domain = record.domain._id;
                Cellar.add(record, {
                    success: function () {
                        Notification.notify.success('Save record : success');
                        $rootScope.$broadcast(Common.events.cellar.update);
                        $scope.close();
                    },
                    error: function () {
                        Notification.notify.error('Save record : error');
                    }
                });
            };
        }
    };

});