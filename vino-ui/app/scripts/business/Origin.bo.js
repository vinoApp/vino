angular.module('vino.business').factory('Origin',
    function ($resource) {

        // Backend
        var Origin = $resource("/api/origins/:type");

        // BO
        return {
            getAllRegions: function () {
                return Origin.query({
                    type: 'regions'
                });
            },
            getAllAOCS: function (handler) {
                return Origin.query({
                    type: 'aocs'
                }, handler);
            }
        };
    });