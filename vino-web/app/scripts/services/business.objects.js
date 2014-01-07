'use strict';

angular.module('vino.services').factory('Bottles', function ($resource) {

    var Bottles = $resource('/rest/bottles/:mode/:id', {mode: '@mode', id: '@id'}, {
        'getByBarCode': {method: 'GET', params: {mode: 'barcode'}},
        'getById': {method: 'GET', params: {mode: 'id'}}
    });
    return angular.extend(Bottles, {
    });
});

angular.module('vino.services').factory('Origins', function ($resource) {

    var Origins = $resource('/rest/origins', {});
    return angular.extend(Origins, {
    });
});

angular.module('vino.services').factory('Pendings', function ($resource) {

    var Pendings = $resource('/rest/pendings/:barcode', {id: '@barcode'});
    return angular.extend(Pendings, {
    });
});

angular.module('vino.services').factory('Domains', function ($resource) {

    var Domains = $resource('/rest/domains/:id', {id: "@id"}, {});
    return angular.extend(Domains, {
    });
});

angular.module('vino.services').factory('DomainBottles', function ($resource) {

    var DomainBottles = $resource('/rest/domains/:id/bottles', {id: "@id"}, {});
    return angular.extend(DomainBottles, {
    });
});

angular.module('vino.services').factory('Cellar', function ($resource) {

    var Cellar = $resource('/rest/cellar', {}, {
        'load': {method: 'POST'},
        'unload': {method: 'DELETE'}
    });
    return angular.extend(Cellar, {
    });
});