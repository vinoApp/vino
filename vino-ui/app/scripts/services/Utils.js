angular.module('vino.services').service('Utils', function ($filter, Notification) {

    return {

        displayError: function (response, defaultError) {

            if (response.data.status === 'ERROR') {
                var i18n = $filter('i18n')('notification.' + response.data.message);
                if (i18n) {
                    Notification.notify.error($filter('i18n')('notification.' + response.data.message));
                } else if (defaultError) {
                    Notification.notify.error($filter('i18n')(defaultError));
                } else {
                    Notification.notify.error($filter('i18n')('notification.GENERIC_ERROR'));
                }
            }
        }
    }

});
