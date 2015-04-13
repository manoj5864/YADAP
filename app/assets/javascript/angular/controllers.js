/* Controllers */

(function(angular){
    'use strict';

    var yadapModule = angular.module('yadapModule');

    yadapModule.controller('CompaniesTableCtrl', function($scope, DTOptionsBuilder, DTColumnBuilder) {

    var vm = this;
    vm.dtOptions = DTOptionsBuilder.newOptions()
          .withOption('sAjaxSource', '/companies')
          .withDataProp('data')
          .withOption('order', [[1, 'asc']])
          .withDisplayLength(10)
          .withOption('processing', true)
          .withOption('serverSide', true)
          .withOption('pagingType', 'full_numbers')
          .withBootstrap()
          .withOption('fnServerData', function(sSource, aoData, fnCallback) {
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
          DTColumnBuilder.newColumn('thumb_url').withTitle('')
          .withOption("bSortable", false).withOption("bSortable", false),
          DTColumnBuilder.newColumn('name').withTitle('Name'),
          DTColumnBuilder.newColumn('high_concept').withTitle('Overview')
          .withOption("bSearchable", false).withOption("bSortable", false)
      ];

    });

    yadapModule.factory('DTLoadingTemplate', dtLoadingTemplate);

    function dtLoadingTemplate() {
        return {
            html: '<img src="http://unakravets.com/images/loading.gif">'
        };
    }

    yadapModule.controller('SimilarCompaniesCtrl', function($scope, $http) {
        $scope.init = function(id) {
            $scope.id = id;
        };

        $http.get('/companies/getSimilarCompanies/' + $scope.id).
        success(function(data, status, headers, config) {
            $scope.similarCompanies = data;
        }).
        error(function(data, status, headers, config) {

        });
    });

    yadapModule.filter("sanitize", ['$sce', function($sce) {
      return function(htmlCode){
        return $sce.trustAsHtml(htmlCode);
      };
    }]);

})(window.angular);