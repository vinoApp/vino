angular.module('vino.ui').controller("DomainCtrl", function ($scope, $routeParams, $filter, Notification, Domain) {

    var mode = $routeParams.mode;
    if (!mode) {
        mode = 'view';
    }

    var domainId = $routeParams.id;
    if (mode != 'new' && !$routeParams.id) {
        console.log('Error no id provided (mode : ' + mode + ') !');
        return;
    }

    angular.extend($scope, {

        isViewing: function () {
            return mode === 'view';
        },
        isEditing: function () {
            return mode === 'edit';
        },
        isCreating: function () {
            return mode === 'new';
        },

        save: function () {

            Domain.createOrUpdate($scope.domain, {
                success: function () {
                    if ($routeParams.id) {
                        Notification.notify.success($filter('i18n')('domain.update.success'));
                    } else {
                        Notification.notify.success($filter('i18n')('domain.create.success'));
                    }
                    $scope.cancel();
                },
                error: function () {
                    if ($routeParams.id) {
                        Notification.notify.error($filter('i18n')('domain.update.error'));
                    } else {
                        Notification.notify.error($filter('i18n')('domain.create.error'));

                    }
                }
            });
        },

        cancel: function () {
            $scope.redirect('/domains');
        }
    });

    if ($scope.isEditing() || $scope.isViewing()) {
        // Edit mode
        $scope.domain = Domain.get({
            id: domainId
        });
    } else if ($scope.isCreating()) {
        // Creation mode
        $scope.domain = Domain.build();
    }

    if ($scope.isViewing()) {
        $scope.records = Domain.query({
            id: domainId,
            action: 'records'
        });
    }
});