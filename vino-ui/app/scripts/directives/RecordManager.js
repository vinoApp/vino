angular.module("vino.ui").directive("recordManager", function ($filter, Common, CellarContent, Notification) {

    return {
        restrict: 'E',
        scope: {
            modalId: '@',
            managedRecord: '='
        },
        templateUrl: 'partials/record.tmpl.html',
        link: function ($scope, elt) {
            $scope.close = function () {
                $(elt).modal('hide');
            };
        },
        controller: function ($scope, $rootScope) {


            $scope.$watch("managedRecord", function (managedRecord) {

                if (!$scope.managedRecord) {
                    return
                }
                if ($scope.managedRecord.domain) {
                    $scope.selectedAOC = _.cloneDeep(managedRecord.domain.origin);
                }
                $scope.record = _.cloneDeep($scope.managedRecord);
                $scope.record.quantity = 0;

            });

            angular.extend($scope, {

                save: function (record) {
                    record.domain = record.domain._id;
                    CellarContent.in(record.cellar._id, record, record.quantity, {
                        success: function () {
                            Notification.notify.success($filter('i18n')('cellar.record.update.success'));
                            $rootScope.$broadcast(Common.events.cellar.update);
                            $scope.close();
                            $scope.record = null;
                        },
                        error: function () {
                            Notification.notify.error($filter('i18n')('cellar.record.update.error'));
                        }
                    });
                },

                isEditing: function () {
                    return $scope.record && $scope.record._id;
                },

                isCreating: function () {
                    return !$scope.record || !$scope.record._id;
                }

            });
        }
    };

});