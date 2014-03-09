'use strict';

angular.module('vino.ui', ['ngRoute', 'vino.business', 'vino.services'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/Main.html'
            })
            .when('/domains', {
                templateUrl: 'views/Domains.html'
            })
            .when('/domains/new', {
                templateUrl: 'views/DomainEditor.html'
            })
            .when('/domains/:id/view', {
                templateUrl: 'views/Domain.html'
            })
            .when('/domains/:id/edit', {
                templateUrl: 'views/DomainEditor.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });


angular.module('vino.business', ['ngResource']);
angular.module('vino.services', []);