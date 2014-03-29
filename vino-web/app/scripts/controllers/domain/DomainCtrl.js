angular.module('vino.ui').controller("DomainCtrl", function ($scope, $routeParams, Notification, Domain) {

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
                        Notification.notify.success('Domain successfuly updated');
                    } else {
                        Notification.notify.success('Domain successfuly created');
                    }
                    $scope.cancel();
                },
                error: function () {
                    Notification.notify.error('Error during domain update');
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