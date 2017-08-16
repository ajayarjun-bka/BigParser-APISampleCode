# This is a simple python example for BigParser API which shows:
# 1. How to login and obtain an authId
# 2. Fetching the grid headers & data from the Grid using gridId provided :
# 2a. Fetching the grid headers
# 2b. Fetching the grid data




import requests
import json

'''
    This method makes generic post calls
    @param endPoint
            Target URI
    @param headers
            Headers to be passed for the current request
    @param data
            Body of the post request
    @return String returns the response as JSON Object
'''


def makePostCall(uri, headers, data):
    try:
        response = requests.post(uri, data=json.dumps(data), headers=headers)
        response.raise_for_status()
        if response.status_code == 200:
            responseData = json.loads(response.text)
            return responseData
    except requests.exceptions.HTTPError as err:
        print(err.response.text)
    except requests.exceptions.Timeout as err:
        print(err.response.text)
    except requests.exceptions.ConnectionError as err:
        print(err.response.text)


'''
    This method makes generic get calls
    @param endPoint
            Target URI
    @param headers
            Headers to be passed for the current request
    @param data
            Body of the post request
    @return String returns the response as JSON Object
'''


def makeGetCall(uri, headers):
    try:
        response = requests.get(uri, headers=headers)
        response.raise_for_status()
        if response.status_code == 200:
            responseData = json.loads(response.text)
            return responseData
    except requests.exceptions.HTTPError as err:
        print(err.response.text)
    except requests.exceptions.Timeout as err:
        print(err.response.text)
    except requests.exceptions.ConnectionError as err:
        print(err.response.text)


# 1. How to login and obtain an authId <START>
# User login. Please Replace with your respective emailId and password below


loginUri = "https://www.bigparser.com/APIServices/api/common/login"
loginRequest = {'emailId': 'arjun.bka@gmail.com', 'password': 'AjayArjun'}
loginHeaders = {'content-type': 'application/json'}

# Making API call to get the authId
loginResponse = makePostCall(loginUri, loginHeaders, loginRequest)
authId = loginResponse['authId']
print(authId)

# 1.How to login and obtain an authId < END >

# 2.Fetching the grid headers & data from the Grid using gridId provided < START >
# Replace with the gridId you want to query

gridId = "57a34b2de4b05fc193e80f32"

# 2a. Get the grid headers
# setting the url for getting grid header

queryTableHeaderURL = "https://www.bigparser.com/APIServices/api/grid/headers?gridId=" + gridId
requestHeaders = {'content-type': 'application/json', 'authId': authId}

# Making API call to get the headers of grid
queryTableHeaderResponse = makeGetCall(queryTableHeaderURL, requestHeaders)
print(queryTableHeaderResponse)
# Fetching Header< END >

# 2b.Getting the rows of the grid

# startIndex and endIndex are * Optional *.You can use them in case you need pagination. In case you are not using the start & end indexes, the
# queryTableURL would be like: String queryTableURL = "https://www.bigparser.com/APIServices/api/query/table

queryTableURL = "https://www.bigparser.com/APIServices/api/query/table?startIndex=0&endIndex=50"

# Making API call to get the data rows of grid String
# Replace rowcount with the no.of rows you need.You can also customize the queryTableRequest to add additional parameters

queryTableRequest = {"gridId": gridId, "rowCount": 50}
queryTableResponse = makePostCall(queryTableURL, requestHeaders, queryTableRequest)
print(queryTableResponse)
# Fetching Data< END >