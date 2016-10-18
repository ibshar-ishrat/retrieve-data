package com.example.ibshar.retrievedata;

/**
 * Created by Ibshar on 09/06/2016.
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JSONParser
{

    static InputStream is = null;    //data in bytes
    static JSONObject jObj = null;  //json object
    static String json = "";     // data in string format

    // constructor
    public JSONParser()
    {

    }

    // function get json from url
    // by making HTTP POST or GET method
    // return type of JSONObject
    public JSONObject makeHttpRequest(String url, String method)
    {

        // Making HTTP request
        try {
           // check for request method
            if(method == "POST")
            {
                // request method is POST
                // defaultHttpClient
                // we can retrieve and send data via httpClient
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                // httpPost determines that the requested method is post
                HttpPost httpPost = new HttpPost(url);
           //     httpPost.setEntity(new UrlEncodedFormEntity(params));

                //httpResponse stores the response of the request locally
                HttpResponse httpResponse = httpClient.execute(httpPost);
                //httpEntity helps to extract the data from response
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }
            else if(method == "GET")
            {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
             //   String paramString = URLEncodedUtils.format(params, "utf-8");
               // url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8); // converts byte stream to character stream
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        }
        catch (Exception e)
        {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(json);
        }
        catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}
