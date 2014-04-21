angular.module('vino.business').factory("Common", function () {

    return {
        events: {
            cellar: {
                update: 'CELLAR_UPDATE_EVENT'
            }
        }
    }
});