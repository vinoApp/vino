angular.module('vino.services').directive('file', function () {
    return {
        scope: {
            file: '='
        },
        link: function (scope, element) {
            element.bind('change', function (event) {
                var files = event.target.files;
                scope.file = files[0];
                scope.$apply();
            });
        }
    };
});

angular.module('vino.services').directive('domainView', function () {

    return {
        templateUrl: 'partials/domain.tmpl.html',
        scope: {
            domain: "=domain"
        },
        link: function (scope, element, attrs) {

        }
    };
});
