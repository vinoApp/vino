angular.module('vino.business').factory("Domain",
    function ($resource) {

        // Backend
        var Domain = $resource("/api/domains/:id");

        // BO
        return angular.extend(Domain, {
            saveOrUpdate: function (domain, callbacks) {
                var cloned = angular.extend(new Domain(), _.cloneDeep(domain));
                cloned.origin = cloned.origin._id;
                cloned.$save(callbacks.success, callbacks.error);
            }
        });
    });