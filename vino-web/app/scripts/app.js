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
            .when('/cellar', {
                templateUrl: 'views/cellar.html',
                controller: 'CellarCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });


angular.module('vino.services', ['ngResource']);