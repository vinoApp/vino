angular.module('vino.ui')
    .controller("MainCtrl", function ($scope, Stats) {

        $scope.stockByVintageOpts = {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: 'Stock actuel / millésime'
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
            },
            series: [
                {
                    name: 'Stock actuel',
                    type: 'pie',
                    data: []
                }
            ]
        };

        Stats.getStockByVintage(function (data) {
            $scope.stockByVintageOpts.series[0].data = $scope.stockByVintageOpts.series[0].data.concat(
                _.map(data, function (item) {
                    return ['' + item.vintage, item.count];
                })
            );
        });

    });