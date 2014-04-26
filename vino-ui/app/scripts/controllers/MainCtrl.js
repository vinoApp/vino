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

        $scope.stockByDomainOpts = angular.extend({
            title: {
                text: 'Stock actuel / chateau'
            },
            series: [
                {
                    name: 'Stock actuel',
                    type: 'pie',
                    data: []
                }
            ]
        }, basePieOpts);

        Stats.getStockByVintage(function (data) {
            $scope.stockByVintageOpts.series[0].data = $scope.stockByVintageOpts.series[0].data.concat(
                _.map(data, function (item) {
                    return ['' + item.vintage, item.count];
                })
            );
        });

        Stats.getStockByDomain(function (data) {
            $scope.stockByDomainOpts.series[0].data = $scope.stockByDomainOpts.series[0].data.concat(
                _.map(data, function (item) {
                    return [item.domain.name, item.count];
                })
            );
        });

    });