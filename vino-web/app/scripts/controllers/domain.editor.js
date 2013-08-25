'use strict';

angular.module('vino.ui')
    .controller('DomainEditorCtrl', function ($scope, $routeParams, $q, Common, Domains, Origins) {

        // Promises manager
        $scope.originsDefer = $q.defer();
        $scope.domainDefer = $q.defer();

        // Fix angularJS bug (objects comparison purpose) in order to fill
        // the combo box field with the domain origin
        $q.all([$scope.originsDefer.promise, $scope.domainDefer.promise]).then(function () {
            for (var i = 0; i < $scope.origins.length; i++) {
                if ($scope.origins[i].id == $scope.domain.origin.id) {
                    $scope.domain.origin = $scope.origins[i];
                    break;
                }
            }
        });

        // Init model
        $scope.current = {};
        $scope.domain = new Domains();
        Origins.query(function (origins) {
            $scope.origins = origins;
            $scope.originsDefer.resolve();
        });

        // Retrieve the domain (editor mode)
        if ($routeParams['domainID']) {
            Domains.get({id: $routeParams['domainID']}, function (response) {
                $scope.domain = response.data;
                $scope.domainDefer.resolve();
            });
        }

        // Watch the selected file in order to computing it
        $scope.$watch("current.file", function (file) {
            if ($.isEmptyObject(file)) {
                return;
            }
            $scope.computeImage(file);
        });

        /**
         * Compute base64 format of the chosen image
         * and associate it with the current item
         * @param f
         */
        $scope.computeImage = function (f) {

            // Check the type of the selected file
            if (!f.type.match('image.*')) {
                return;
            }

            // Load the content of the image
            var reader = new FileReader();

            // Called when the data is loaded
            reader.onload = function (e) {
                $scope.domain.sticker = e.target.result.substring(e.target.result.indexOf("base64,") + 7);
                $scope.$apply();
            };
            reader.readAsDataURL(f);
        };

        // Save and cancel actions

        $scope.save = function () {
            Domains.save($scope.domain, function (response) {
                console.log(response);
                $scope.cancel();
            });
        };

        $scope.cancel = function () {
            $scope.redirect('/domains');
        };
    });
