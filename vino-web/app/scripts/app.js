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
            .when('/domains/:mode', {
                templateUrl: 'views/Domain.html'
            })
            .when('/domains/:id', {
                templateUrl: 'views/Domain.html'
            })
            .when('/domains/:id/:mode', {
                templateUrl: 'views/Domain.html'
            })
            .when('/cellar', {
                templateUrl: 'views/Cellar.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });


angular.module('vino.business', ['ngResource']);
angular.module('vino.services', []);