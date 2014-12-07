angular.module('vino.ui')
    .controller('StatsCtrl', function ($scope, Stats) {

        var basePieOpts = {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true
                    },
                    showInLegend: true
                }
            }
        };

        var hasData = function () {
            return this.series[0].data && this.series[0].data.length > 0;
        };

        var options = Highcharts.getOptions();
        if (!options.colors) {
            options.colors = Highcharts.map(options.colors, function (color) {
                return {
                    radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
                    stops: [
                        [0, color],
                        [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
                    ]
                };
            });
        }

        angular.extend($scope, {

            stockByVintageOpts: angular.extend({
                title: {
                    text: 'Stock actuel / millésime'
                },
                series: [
                    {
                        name: 'Stock actuel',
                        type: 'pie',
                        data: []
                    }
                ],
                hasData: hasData
            }, basePieOpts),

            stockByDomainOpts: angular.extend({
                title: {
                    text: 'Stock actuel / chateau'
                },
                series: [
                    {
                        name: 'Stock actuel',
                        type: 'pie',
                        data: []
                    }
                ],
                hasData: hasData
            }, basePieOpts),

            stockByAOCOpts: angular.extend({
                title: {
                    text: 'Stock actuel / AOC'
                },
                series: [
                    {
                        name: 'Stock actuel',
                        type: 'pie',
                        data: []
                    }
                ],
                hasData: hasData
            }, basePieOpts)
        });

        var loadStats = function () {

            var stats = Stats.forCellar($scope.cellar._id);

            stats.getStockByVintage(function (data) {
                $scope.stockByVintageOpts.series[0].data = $scope.stockByVintageOpts.series[0].data.concat(
                    _.map(data, function (item) {
                        return ['' + item.vintage, item.count];
                    })
                );
            });

            stats.getStockByDomain(function (data) {
                $scope.stockByDomainOpts.series[0].data = $scope.stockByDomainOpts.series[0].data.concat(
                    _.map(data, function (item) {
                        return [item.domain.name, item.count];
                    })
                );
            });

            stats.getStockByAOC(function (data) {
                $scope.stockByAOCOpts.series[0].data = $scope.stockByAOCOpts.series[0].data.concat(
                    _.map(data, function (item) {
                        return [item.aoc.name, item.count];
                    })
                );
            });
        };

        // Watch selected cellar changes
        $scope.$watch("cellar", function (cellar) {
            if (!cellar) {
                return;
            }
            loadStats();
        });

    });