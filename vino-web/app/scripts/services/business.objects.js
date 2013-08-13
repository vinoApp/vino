'use strict';

angular.module('vino.services').factory('Bottles', function ($resource) {

    var Bottles = $resource('/rest/bottles/:barcode', {barcode: '@barcode'});
    return angular.extend(Bottles, {
    });
});

angular.module('vino.services').factory('PendingBottle', function () {

});

angular.module('vino.services').factory('Cellar', function ($resource) {
    var Cellar = $resource('/rest/cellar', {}, {
        'load': {method: 'POST'},
        'unload': {method: 'DELETE'}
    });
    return angular.extend(Cellar, {
    });
});