package trial;
/*
 * This is a simple java example for BigParser API which shows:
 * 1. How to login and obtain an authId
 * 2. Fetching the grid headers & data from the Grid using gridId provided :
 *      2a. Fetching the grid headers
 *      2b. Fetching the grid data
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BigParserAPIJavaDemo {

    public static void main(String[] args) {

        //1. How to login and obtain an authId <START>
        //User login. Please Replace with your respective emailid and password below
        String loginRequest = "{\"emailId\": \"bigparser.api@gmail.com\"," +
                "  \"password\": \"apidemo_pwd\"}";
        String loginUri = "https://www.bigparser.com/APIServices/api/common/login";

        HashMap<String, String> loginHeaders = new HashMap<String, String>();
        loginHeaders.put("Content-Type", "application/json");

        String loginResponse = makePOSTCall(loginUri, loginHeaders, loginRequest);
        System.out.println("LOGIN RESPONSE : " + loginResponse + "\n");

        int startOfAuthID = loginResponse.indexOf("authId");
        //fetch authId for future calls
        String authId = loginResponse.substring(startOfAuthID + 9, startOfAuthID + 45);
        System.out.println("authId : " + authId + "\n");
        //1. How to login and obtain an authId <END>


        //2.Fetching the grid headers & data from the Grid using gridId provided <START>
        //Replace with the gridId you want to query
        String gridId = "57a34b2de4b05fc193e80f32";

        //2a. Get the grid headers

        //setting the url for getting grid header
        String queryTableHeaderURL = "https://www.bigparser.com/APIServices/api/grid/headers?gridId=" + gridId;

        //setting the API request headers
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("authId", authId);


        //Making API call to get the headers of grid
        String queryTableHeaderResponse = makeGETCall(queryTableHeaderURL, requestHeaders);
        System.out.println("queryTable Header RESPONSE : " + queryTableHeaderResponse + "\n");

        //2b. Getting the rows of the grid
        //Replace rowcount with the no. of rows you need. You can also customize the queryTableRequest to add additional parameters
        String queryTableRequest = "{\"gridId\":\"" + gridId + "\",\"rowCount\":50}";

        /*startIndex and endIndex are *Optional*. You can use them in case you need pagination.
        In case you are not using the start & end indexes, the queryTableURL would be like :
        String queryTableURL = "https://www.bigparser.com/APIServices/api/query/table */
        String queryTableURL = "https://www.bigparser.com/APIServices/api/query/table?startIndex=0&endIndex=50";

        //Making API call to get the data rows of grid
        String quertTableResponse = makePOSTCall(queryTableURL, requestHeaders, queryTableRequest);
        System.out.println("queryTable Rows RESPONSE : " + quertTableResponse + "\n");
        //2.Fetching the grid headers & data from the Grid using gridId provided <END>
    }

    //This is a generic java method which can be used for any REST GET API call
    private static String makeGETCall(String uri, HashMap<String, String> headers) {

        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    //This is a generic java method which can be used for any REST POST API call
    private static String makePOSTCall(String uri, HashMap<String, String> headers, String body) {

        StringBuffer response = new StringBuffer();
        try {

            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}