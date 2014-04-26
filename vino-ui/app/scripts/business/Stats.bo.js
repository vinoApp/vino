angular.module('vino.business').factory("Stats",
    function ($resource) {

        // Backend
        var Stats = $resource("/api/stats/:indicator");

        // BO
        return angular.extend(Stats, {
            getStockByVintage: function (handler) {
                return Stats.query({
                    indicator: 'cellarStockByVintage'
                }, handler);
            },
            getStockByDomain: function (handler) {
                return Stats.query({
                    indicator: 'cellarStockByDomain'
                }, handler);
            }
        });
    });