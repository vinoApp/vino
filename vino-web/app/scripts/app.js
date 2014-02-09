'use strict';

angular.module('vino.ui', ['vino.business'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });


angular.module('vino.business', ['ngResource']);