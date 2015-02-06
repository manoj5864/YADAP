/* Controllers */

(function(angular){
    'use strict';

    var yadapModule = angular.module('yadapModule');

    yadapModule.controller('CompaniesTableCtrl', function($scope, DTOptionsBuilder, DTColumnBuilder){

    var vm = this;
    vm.dtOptions = DTOptionsBuilder.newOptions()
          .withOption('sAjaxSource', '/companies')
          .withDataProp('data')
          .withOption('sorting', [[0, null], [1, 'asc']])
          .withDisplayLength(10)
          .withOption('processing', true)
          .withOption('serverSide', true)
          .withOption('pagingType', 'full_numbers')
          .withBootstrap()
          .withOption('fnServerData', function (sSource, aoData, fnCallback) {
              $.ajax({
                  "dataType": "json",
                  "type": "POST",
                  "url": '/companies',
                  "data": aoData,
                  "success": function (json) {
                      fnCallback(json);
                      $scope.$apply();
                  }
              });
          });


      vm.dtColumns = [
          DTColumnBuilder.newColumn('thumb_url').withTitle(''),
          DTColumnBuilder.newColumn('name').withTitle('Name'),
          DTColumnBuilder.newColumn('high_concept').withTitle('Overview')
      ];

    });

    yadapModule.factory('DTLoadingTemplate', dtLoadingTemplate);
    function dtLoadingTemplate() {
        return {
            html: '<img src="http://unakravets.com/images/loading.gif">'
        };
    }

})(window.angular);