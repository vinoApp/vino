angular.module('vino.ui')
    .controller("MainCtrl", function ($scope, Stats) {

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

        Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
            return {
                radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
                stops: [
                    [0, color],
                    [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
                ]
            };
        });

        $scope.stockByVintageOpts = angular.extend({
            title: {
                text: 'Stock actuel / millésime'
            },
            series: [
                {
                    name: 'Stock actuel',
                    type: 'pie',
                    data: []
                }
            ]
        }, basePieOpts);

        // AOC + Drilldown on nested domains
        var drillDownByDomainSeries = {};
        $scope.stockByAOCOpts = angular.extend({
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
            drilldown: {
                series: [ drillDownByDomainSeries ]
            }
        }, basePieOpts);


        Stats.getStockByVintage(function (data) {
            $scope.stockByVintageOpts.series[0].data = $scope.stockByVintageOpts.series[0].data.concat(
                _.map(data, function (item) {
                    return ['' + item.vintage, item.count];
                })
            );
        });

        Stats.getStockByDomain(function (data) {
            _.each(data, function (item) {

                var aocName = item.domain.origin.name;
                var domainName = item.domain.name;

                var record = _.find($scope.stockByAOCOpts.series[0].data, {name: aocName});
                if (!record) {
                    drillDownByDomainSeries[aocName] = {
                        id: aocName,
                        data: [domainName, item.count]
                    };
                    $scope.stockByAOCOpts.series[0].data.push({
                        name: aocName,
                        y: item.count,
                        drilldown: aocName
                    });
                } else {
                    record.y += item.count;
                    drillDownByDomainSeries[aocName].data = drillDownByDomainSeries[aocName].data
                        .concat([domainName, item.count]);
                }
            });

            $("#stockByAocElt").highcharts($scope.stockByAOCOpts);

        });
    });