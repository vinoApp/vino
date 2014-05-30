angular.module("vino.ui").directive("cellarPicker", function (Cellar) {

    return {
        restrict: 'E',
        scope: {
            "ngModel": "=",
            "ngDisabled": "="
        },
        template: "<select ng-options='cellar._id for cellar in cellars'></select>",
        link: function ($scope, elt) {

            var format = function (cellar) {
                return cellar.name;
            };

            var findIndex = function (value) {
                return _.findIndex($scope.cellars, function (cellar) {
                    if (_.isObject(value)) {
                        return cellar._id === value._id;
                    }
                    return cellar._id === value;
                });
            };

            var element = $(elt);

            function render() {

                element.select2({
                    data: {
                        results: $scope.cellars,
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
                        var cellar = $scope.cellars[findIndex($scope.ngModel)];
                        callback(cellar);
                    },
                    width: '100%'
                });
            }

            $scope.$watchCollection("[cellars.length, ngModel]", function () {

                if (!$scope.cellars || $scope.cellars.length < 1) {
                    return;
                }
                render();
            });

            element.change(function () {
                $scope.$apply(function () {
                    $scope.ngModel = $scope.cellars[findIndex(element.val())];
                });
            });

            $scope.$watch("ngDisabled", function () {
                element.prop('disabled', $scope.ngDisabled);
                render();
            });


            $scope.cellars = Cellar.query(function(cellars){
                if(!$scope.ngModel && cellars && cellars.length > 0) {
                    $scope.ngModel = cellars[0];
                }
            });
        }
    };

});