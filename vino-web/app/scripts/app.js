'use strict';

angular.module('vino.ui', ['vino.services'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/bottles', {
                templateUrl: 'views/bottles.html',
                controller: 'BottlesCtrl'
            })
            .when('/bottle/:bottleID', {
                templateUrl: 'views/bottle.html',
                controller: 'BottleCtrl'
            })
            .when('/domains', {
                templateUrl: 'views/domains.html',
                controller: 'DomainsCtrl'
            })
            .when('/domains/:mode/:domainID', {
                templateUrl: 'views/domain.html',
                controller: 'DomainCtrl'
            })
            .when('/cellar', {
                templateUrl: 'views/cellar.html',
                controller: 'CellarCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });


angular.module('vino.services', ['ngResource']);