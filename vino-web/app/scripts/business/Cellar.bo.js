angular.module('vino.business').factory("Cellar",
    function ($resource) {

        // Backend
        var Cellar = $resource("/api/cellar");

        // BO
        return angular.extend(Cellar, {

        });
    });