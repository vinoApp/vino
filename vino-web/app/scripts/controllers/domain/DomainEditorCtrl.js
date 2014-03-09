angular.module('vino.ui').controller("DomainEditorCtrl", function ($scope, $routeParams, Notification, Domain) {

    // Provided domain ID
    var domainId = $routeParams.id;

    // Edit mode
    if ($routeParams.id) {
        $scope.domain = Domain.get({
            id: domainId
        });
    }
    // Creation mode
    else {
        $scope.domain = new Domain();
    }

    $scope.save = function () {

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
    };

    $scope.cancel = function () {
        $scope.redirect('/domains');
    };
});