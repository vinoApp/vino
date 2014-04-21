angular.module("vino.ui").directive("filePicker", function () {

    return {
        restrict: 'E',
        scope: {
            ngModel: '='
        },
        template: '<input id="domainSticker" type="file" accept="image/jpeg"/>',
        link: function ($scope, elt) {

            var BASE_64_PREFIX = "base64,";

            elt.bind('change', function (event) {

                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                    $scope.$apply(function () {
                        var result = loadEvent.target.result;
                        if (!result) {
                            return;
                        }
                        $scope.ngModel = result.substring(loadEvent.target.result.indexOf(BASE_64_PREFIX) + BASE_64_PREFIX.length);
                    });
                };
                reader.readAsDataURL(event.target.files[0]);
            });
        }
    }

});