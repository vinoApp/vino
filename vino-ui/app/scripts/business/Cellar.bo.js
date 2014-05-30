angular.module('vino.business')
    .factory('Cellar',function ($resource) {

        var Cellar = $resource('/api/cellar/:id');

        return angular.extend(Cellar, {});

    }).factory('CellarContent', function ($resource) {

        var CellarContent = $resource('/api/cellar/:id/content');

        var prepareToServer = function (type, record, qty) {

            var cloned = _.cloneDeep(record);

            return angular.extend(new CellarContent(), {
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
            });
        };

        return angular.extend(CellarContent, {
            in: function (cellarKey, record, qty, handlers) {
                var content = prepareToServer('IN', record, qty);
                return content.$save({id: cellarKey}, handlers.success, handlers.error);
            },
            out: function (cellarKey, record, qty, handlers) {
                var content = prepareToServer('OUT', record, qty);
                return content.$save({id: cellarKey}, handlers.success, handlers.error);
            }
        });
    });