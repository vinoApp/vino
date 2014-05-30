angular.module('vino.business').factory("Stats",
    function ($resource) {

        // Backend
        var Stats = $resource("/api/stats/:cellarKey/:indicator");

        // BO
        return angular.extend(Stats, {

            _cellarKey: null,

            forCellar: function (cellarKey) {
                this._cellarKey = cellarKey;
                return this;
            },

            getStockByVintage: function (handler) {
                return Stats.query({
                    cellarKey: Stats._cellarKey,
                    indicator: 'cellarStockByVintage'
                }, handler);
            },
            getStockByDomain: function (handler) {
                return Stats.query({
                    cellarKey: Stats._cellarKey,
                    indicator: 'cellarStockByDomain'
                }, handler);
            },
            getStockByAOC: function (handler) {
                return Stats.query({
                    cellarKey: Stats._cellarKey,
                    indicator: 'cellarStockByAOC'
                }, handler);
            }
        });
    });