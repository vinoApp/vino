/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

angular.module('vino.ui')
    .controller('CellarsCtrl', function ($scope, $routeParams, $filter, Notification, Utils, Cellar) {

        var loadCellars = function () {
            $scope.cellars = Cellar.query();
        };

        var createOrEditCellar = function (cellar, mode) {
            return Cellar.save(cellar,
                function () {
                    Notification.notify.success($filter('i18n')('cellar.' + mode + '.success'));
                    loadCellars();
                }, function (response) {
                    Utils.displayError(response, 'cellar.' + mode + '.error')
                });
        };

        loadCellars();

        angular.extend($scope, {
            create: function () {
                $scope.cellars.push({ '@class': 'com.vino.domain.WineCellar' });
            },
            edit: function (cellar, mode) {
                createOrEditCellar(cellar, 'edit');
            },
            remove: function (cellar) {
                Notification.dialog.confirm($filter('i18n')('action.delete.cellar.confirm'), null,
                    function (yes) {
                        if (yes) {
                            Cellar.delete({ id: cellar._id },
                                function () {
                                    Notification.notify.success($filter('i18n')('cellar.delete.success'));
                                    loadCellars();
                                }, function (response) {
                                    Utils.displayError(response, 'cellar.delete.error');
                                }
                            );
                        }
                    });
            }
        });

    });