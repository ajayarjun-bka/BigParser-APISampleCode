angular.module('bigparser-api-demo', [])
  .controller('HomeController', function ($http) {


    //User login. Please Replace with your respective emailid and password below
    var loginURL = 'https://www.bigparser.com/APIServices/api/common/login';
    var loginRequestData = {
      'emailId': 'bigparser.api@gmail.com',
      'password': 'apidemo_pwd'
    };

    //Function to fetch grid headers
    var getGridHeaders = function (authId, gridId) {
      //setting the url for getting grid header
      var getGridURL = 'https://www.bigparser.com/APIServices/api/grid/headers?gridId=' + gridId;
      //setting the API request headers
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
          console.log('Grid Headers Response:');
          console.log(response.data);
        }
      });
    };


    // function to fetch data from grid
    var getGridData = function (authId, gridId) {
      //startIndex and endIndex are Optional. You can use them in case you need pagination.
      var queryTableURL = 'https://www.bigparser.com/APIServices/api/query/table?startIndex=0&endIndex=50';
       //setting the API request headers and post parameters
      var gridDataRequest = {
        method: 'POST',
        url: queryTableURL,
        headers: {
          'Content-Type': 'application/json',
          'authId': authId
        },
        data: {
          'gridId': gridId,
          'rowCount': 50
        }
      }
      $http(gridDataRequest).then(function (response) {
        if (response.data) {
          console.log('Grid Data Response:');
          console.log(response.data);
        }
      });

    }


// HTTP post for login.
    //specify the gridId you want to query
    $http.post(loginURL, loginRequestData).
      then(function (response) {
        if (response.data && response.data.authId) {
          getGridHeaders(response.data.authId, '57a34b2de4b05fc193e80f32');
          getGridData(response.data.authId, '57a34b2de4b05fc193e80f32');
        }
      });
  });