angular.module("vino.ui").directive("recordManager", function (Common, Cellar, Notification) {

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

            var mode;

            $scope.$watch('managedRecord', function (managedRecord) {
                if (!managedRecord) {
                    mode = 'new';
                    $scope.record = {
                        code: {
                            value: ''
                        }
                    };
                } else {
                    mode = 'edit';
                    $scope.selectedAOC = _.cloneDeep(managedRecord.domain.origin);
                    $scope.record = _.cloneDeep(managedRecord);
                    $scope.record.quantity = 0;
                }
            });

            angular.extend($scope, {

                save: function (record) {
                    record.domain = record.domain._id;
                    Cellar.add(record, {
                        success: function () {
                            Notification.notify.success('Save record : success');
                            $rootScope.$broadcast(Common.events.cellar.update);
                            $scope.close();
                            $scope.record = null;
                        },
                        error: function () {
                            Notification.notify.error('Save record : error');
                        }
                    });
                },

                isEditing: function () {
                    return mode === 'edit';
                },

                isCreating: function () {
                    return mode === 'new';
                }

            });
        }
    };

});