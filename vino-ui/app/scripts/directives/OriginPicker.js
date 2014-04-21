angular.module("vino.ui").directive("originPicker", function (Origin) {

    return {
        restrict: 'E',
        scope: {
            ngModel: '=',
            ngRequired: '=',
            ngDisabled: '='
        },
        link: function ($scope, elt) {

            var loadData = function (aocs) {
                $scope.data = {};
                _.each(aocs, function (aoc) {
                    var _regionId = aoc.region._id;
                    var _region = $scope.data[_regionId];
                    if (!_region) {
                        $scope.data[_regionId] = {
                            id: _regionId,
                            name: aoc.region.name,
                            children: []
                        };
                    }
                    var region = $scope.data[_regionId];
                    region.children.push(aoc);
                });
                $scope.data = _.values($scope.data);
            };

            var format = function (item) {
                return item.name;
            };

            var findIndex = function (value) {
                return _.findIndex($scope.aocs, function (aoc) {
                    if (_.isObject(value)) {
                        return aoc._id === value._id;
                    }
                    return aoc._id === value;
                });
            };

            var element = $(elt);

            function render() {

                element.select2({
                    data: {
                        results: $scope.data,
                        text: 'name'
                    },
                    id: function (item) {
                        return item._id;
                    },
                    formatSelection: format,
                    formatResult: format,
                    initSelection: function (element, callback) {
                        if (!$scope.ngModel) {
                            return;
                        }
                        var aoc = $scope.aocs[findIndex($scope.ngModel)];
                        callback(aoc);
                    },
                    width: '100%'
                });
            }

            $scope.$watchCollection("[aocs.length, ngModel]", function () {

                if (!$scope.aocs || $scope.aocs.length < 1) {
                    return;
                }
                render();
            });

            element.change(function () {
                $scope.$apply(function () {
                    $scope.ngModel = $scope.aocs[findIndex(element.val())];
                });
            });

            $scope.$watch("ngDisabled", function () {
                element.prop('disabled', $scope.ngDisabled);
                render();
            });

            $scope.aocs = Origin.getAllAOCS(function (aocs) {
                loadData(aocs);
            });
        }
    }

});