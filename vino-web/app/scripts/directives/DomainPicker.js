angular.module("vino.ui").directive("domainPicker", function (Domain) {

    return {
        restrict: 'E',
        scope: {
            "ngModel": "=",
            "ngDisabled": "="
        },
        template: "<select ng-options='domain._id for domain in domains'></select>",
        link: function ($scope, elt) {

            var format = function (domain) {
                return domain.name + ' (' + domain.origin.name + ')';
            };

            var findIndex = function (value) {
                return _.findIndex($scope.domains, function (domain) {
                    if (_.isObject(value)) {
                        return domain._id === value._id;
                    }
                    return domain._id === value;
                });
            };

            var element = $(elt);

            function render() {

                element.select2({
                    data: {
                        results: $scope.domains,
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
                        var domain = $scope.domains[findIndex($scope.ngModel)];
                        callback(domain);
                    },
                    width: '100%'
                });
            }

            $scope.$watchCollection("[domains.length, ngModel]", function () {

                if (!$scope.domains || $scope.domains.length < 1) {
                    return;
                }
                render();
            });

            element.change(function () {
                $scope.$apply(function () {
                    $scope.ngModel = $scope.domains[findIndex(element.val())];
                });
            });

            $scope.$watch("ngDisabled", function () {
                element.prop('disabled', $scope.ngDisabled);
                render();
            });

            $scope.domains = Domain.query();
        }
    };

});