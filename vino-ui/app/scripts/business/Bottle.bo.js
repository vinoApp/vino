angular.module('vino.business').factory("Bottle",
    function ($resource) {

        // Backend
        var Bottle = $resource("/api/bottles/:id");

        // BO
        return angular.extend(Bottle, {
            get: function (key) {
                return Bottle.get({
                    id: key,
                    isBarCode: false
                });
            },
            getByBarCode: function (barCode) {
                return Bottle.get({
                    id: barCode,
                    isBarCode: true
                });
            }
        });
    });