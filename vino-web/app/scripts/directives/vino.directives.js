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
