'use strict';

angular.module('vino.ui')
    .controller('PendingsCtrl', function ($scope, $q, Bottles, Domains, Pendings, Cellar) {

        // Promises manager
        var defer = $q.defer();

        // Init model
        $scope.currentPending = {};

        // Retrieve entities
        $scope.pendings = Pendings.query();
        $scope.domains = Domains.query();

        // Functions
        $scope.openValidate = function (pending) {
            $("#pendingModal").modal('show');
            $scope.currentPending = pending;
        };

        $scope.validate = function () {

            if (!$scope.currentPending) {
                return;
            }

            // Build bottle object and add it
            var bottle = {
                barcode: $scope.currentPending.barcode,
                vintage: $scope.currentPending.vintage,
                domain: $scope.currentPending.domain
            };

            // Build cellar record and add it (rolled after bottle adding)
            defer.promise.then(function () {
                var params = $.param({
                    bottle: bottle,
                    quantity: $scope.currentPending.qty
                });
                Cellar.save(params, function (response) {
                    console.log(response);
                });
            }).then(function () {

                    Pendings.remove({barcode: $scope.currentPending.barcode}, function (response) {
                        console.log(response);
                    });
                });

            Bottles.save(bottle, function (response) {
                console.log(response);
                defer.resolve();
            });
        };
    })
;
