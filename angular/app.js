angular.module('bigparser-api-demo', [])
  .controller('HomeController', function ($http, $scope) {


    //Replace Username and Password with your credentials
    var loginURL = 'https://www.bigparser.com/APIServices/api/common/login';
    var loginRequestData = {
      'emailId': 'big@bigparser.com',
      'password': 'bigparser'
    };
    var authId = undefined
    var gridId = "57a34b2de4b05fc193e80f32"


    $scope.login = function () {
      $http.post(loginURL, loginRequestData).
      then(function (response) {
        if (response.data && response.data.authId) {
          authId = response.data.authId
        }
      });
    }




    //Function to fetch grid headers
    $scope.getGridHeaders = function () {
      var getGridURL = 'https://www.bigparser.com/APIServices/api/grid/headers?gridId=' + gridId;
      var gridHeadersRequest = {
        method: 'GET',
        url: getGridURL,
        headers: {
          'Content-Type': 'application/json',
          'authId': authId
        }
      }

      $http(gridHeadersRequest).then(function (response) {
        if (response.data) {
          $scope.result = (response.data.columns);
        }
      });
    };


    // function to fetch data from grid
    $scope.getGridData = function () {
      
      var queryTableURL = 'https://www.bigparser.com/APIServices/api/query/table?startIndex=0&endIndex=50';
      var gridDataRequest = {
        method: 'POST',
        url: queryTableURL,
        headers: {
          'Content-Type': 'application/json',
          'authId': authId
        },
        data: {
          'gridId': gridId,
          'rowCount': 2
        }
      }


      $http(gridDataRequest).then(function (response) {
        if (response.data) {
          console.log('Grid Data Response:');
          console.log(response.data.rows);
          $scope.result = response.data.rows
        }
      });

    }


    // HTTP post for login.

    // $http.post(loginURL, loginRequestData).
    //   then(function (response) {
    //     if (response.data && response.data.authId) {
    //       getGridHeaders(response.data.authId, '57a34b2de4b05fc193e80f32');
    //       getGridData(response.data.authId, '57a34b2de4b05fc193e80f32');
    //     }
    //   });
  });
