angular.module('vino.business').factory("Domain",
    function ($resource) {

        // Backend
        var Domain = $resource("/api/domains/:id/:action");

        var updateObjectWithRefs = function (domain) {
            var cloned = angular.extend(new Domain(), _.cloneDeep(domain));
            cloned.origin = cloned.origin._id;
            if (!cloned['@class']) {
                cloned['@class'] = 'com.vino.backend.model.WineDomain';
            }
            return cloned;
        };

        // BO
        return angular.extend(Domain, {
            build: function () {
                return {
                    wineDescription: {},
                    tasting: {},
                    history: {}
                };
            },
            createOrUpdate: function (domain, callbacks) {
                var _domain = updateObjectWithRefs(domain);
                _domain.$save(callbacks.success, callbacks.error);
            }
        });
    });