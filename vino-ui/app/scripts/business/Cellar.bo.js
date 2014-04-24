angular.module('vino.business').factory("Cellar",
    function ($resource) {

        // Backend
        var Cellar = $resource("/api/cellar/:id", { id: '@id' });

        var prepareToServer = function (type, record, qty) {
            var cloned = _.cloneDeep(record);
            return {
                '@class': 'com.vino.backend.model.Movement',
                type: type,
                amount: qty,
                record: {
                    '@class': 'com.vino.backend.model.WineCellarRecord',
                    _id: cloned._id,
                    code: cloned.code,
                    domain: _.isObject(cloned.domain) ? cloned.domain._id : cloned.domain,
                    vintage: cloned.vintage,
                    quantity: cloned.quantity
                }
            }
        };

        // BO
        return angular.extend(Cellar, {
            in: function (record, qty, handlers) {
                Cellar.save(prepareToServer('IN', record, qty), handlers.success, handlers.error);
            },
            out: function (record, qty, handlers) {
                Cellar.save(prepareToServer('OUT', record, qty), handlers.success, handlers.error);
            }
        });
    })
;