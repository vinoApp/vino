angular.module('vino.business').factory("Cellar",
    function ($resource, Notification) {

        // Backend
        var Cellar = $resource("/api/cellar/:id/:qty", {id: '@id', qty: '@qty'}, {
            _in: {method: 'PUT'},
            _out: {method: 'DELETE'}
        });

        // BO
        return angular.extend(Cellar, {
            add: function (record, handlers) {
                record['@class'] = 'com.vino.backend.model.WineCellarRecord';
                Cellar.save(record, handlers.success, handlers.error);
            },
            in: function (record, qty, handlers) {
                Cellar._in({
                    id: record._id,
                    qty: qty
                }, handlers.success, handlers.error);
            },
            out: function (record, qty, handlers) {
                Cellar._out({
                    id: record._id,
                    qty: qty
                }, handlers.success, handlers.error);
            }
        });
    });