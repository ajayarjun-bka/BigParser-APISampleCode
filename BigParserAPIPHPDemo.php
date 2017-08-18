<?php
/* Function to make post call*/
function makePostCall($url, $requestData, array $headers = []) {
  $curl = curl_init($url);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_POST, true);
  curl_setopt($curl, CURLOPT_POSTFIELDS, $requestData);
  setHeaders($curl, $headers);
  return getResponse($curl, curl_exec($curl));
}
/* Function to make get call*/
function makeGetCall($url, array $headers = []) {
  $curl = curl_init($url);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  setHeaders($curl, $headers);
  return getResponse($curl, curl_exec($curl));
}

function setHeaders($curl, array $headers = []) {
  $headers = array_merge(['Content-Type: application/json'], $headers);
  curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
}

function getResponse($curl, $curlResponse) {
  $response = null;
  if ($curlResponse === false) {
    $info = curl_getinfo($curl);
    curl_close($curl);
    die('error occured during curl exec. Additioanl info: ' . var_export($info));
  } else {
    curl_close($curl);
    $response = json_decode($curlResponse, true);
  }
  return $response;
}
/* Replace emailId and Password with your credentials*/
$loginUrl = 'https://www.bigparser.com/APIServices/api/common/login';
$requestData = '{
  "emailId": "bigparser.api@gmail.com",
  "password": "apidemo_pwd"
}';
$loginData = makePostCall($loginUrl, $requestData);
print_r($loginData);
if (!empty($loginData['authId'])) {
  $getGridHeadersURL = "https://www.bigparser.com/APIServices/api/grid/headers?gridId=57a34b2de4b05fc193e80f32";
  $gridHeaders = makeGetCall($getGridHeadersURL, ["authId: {$loginData['authId']}"]);
  print_r($gridHeaders);
  
/*Replace gridId with the one from which you wish to fetch data*/
  $queryTableURL = "https://www.bigparser.com/APIServices/api/query/table?startIndex=0&endIndex=50";
  $requestData = '{
    "gridId": "57a34b2de4b05fc193e80f32",
    "rowCount": 50
  }';
  $gridData = makePostCall($queryTableURL, $requestData, ["authId: {$loginData['authId']}"]);
  print_r($gridData);
}
