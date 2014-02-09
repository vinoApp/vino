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

        Domain.saveOrUpdate($scope.domain, {
            success: function () {
                Notification.notify.success('Domain successfuly updated');
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