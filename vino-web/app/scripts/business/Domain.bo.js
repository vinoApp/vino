angular.module('vino.business').factory("Domain",
    function ($resource) {

        // Backend
        var Domain = $resource("/api/domains/:id");

        // BO
        return angular.extend(Domain, {

        });
    });