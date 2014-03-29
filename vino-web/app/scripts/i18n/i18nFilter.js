'use strict';

angular.module('vino.ui').
    factory('localize', function ($interpolate) {
        return {
            // array to hold the localized resource string entries
            missingKeys: {},

            getLocalizedString:function (value, LocalDictionary, scope) {
                // default the result to an empty string
                var result = '';

                if (_.isEmpty(value)) {
                    return result;
                }

                // make sure the dictionary has valid data
                if (LocalDictionary !== {}) {
                    result = LocalDictionary[value];

                    if (result && !_.isEmpty(scope)) {
                        result = $interpolate(result)(scope);
                    }

                    if (angular.isUndefined(result)) {
                        result = value +' #';
                        if(angular.isUndefined(this.missingKeys[value])) {
                            console.log('missing key:'+ value);
                            this.missingKeys[value]="";
                        }
                    }
                }

                // return the value to the call
                return result;
            }
        };
    }).
    filter('i18n',  function (localize, LocalDictionary) {
        return function (input, $scope) {
            return localize.getLocalizedString(input, LocalDictionary, $scope);
        };
    });