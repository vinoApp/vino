angular.module("vino.ui").directive("originPicker", function () {

    return {
        restrict: 'E',
        scope: {
            ngModel: '=',
            ngRequired: '='
        },
        template: '' +
            '<select id="domainOrigin" ng-required="ngRequired" ng-model="ngModel" ng-options="aoc.name group by aoc.region.name for aoc in aocs">' +
            '</select>',
        controller: function ($scope, Origin) {

            $scope.$watchCollection("aocs", function () {
                if (!$scope.aocs) {
                    return;
                }
                $scope.$watch("ngModel", function () {
                    if (!$scope.ngModel) {
                        return;
                    }
                    var foundAOC = _.find($scope.aocs, {_id: $scope.ngModel._id});
                    if (foundAOC) {
                        $scope.ngModel = foundAOC;
                    }
                });
            });

            $scope.aocs = Origin.getAllAOCS();

        }
    }

});