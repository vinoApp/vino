angular.module('vino.services').directive('domainView', function () {

    return {
        templateUrl: 'partials/domain.view.tmpl.html',
        scope: {
            domain: "=domain"
        },
        link: function (scope, element, attrs) {

        }
    };
});

angular.module('vino.services').directive('domainEdit', function () {

    return {
        templateUrl: 'partials/domain.edit.tmpl.html',
        scope: {
            domain: "=domain"
        },
        link: function (scope, element, attrs) {

        }
    };
});