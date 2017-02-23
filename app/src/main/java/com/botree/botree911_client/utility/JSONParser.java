package com.botree.botree911_client.utility;

/**
 * Created by bhavin on 19/12/16.
 */

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    int connectionTimeOut = 30000;

    public String makeHttpRequest(Context mContext, String url, String method,
                                  JSONObject jsonObject, HashMap<String, String> params) {

        if (method.equals("PATCH")) {
            // request method is PATCH
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("PATCH");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                conn.setRequestProperty("Expect", "100-continue");
                conn.setRequestProperty("access_token", PreferenceUtility.getAccessToken(mContext));

                conn.setReadTimeout(connectionTimeOut);
                conn.setConnectTimeout(connectionTimeOut);

                conn.connect();

                Log.d("AccessToken", PreferenceUtility.getAccessToken(mContext));
                Log.d("Json", jsonObject.toString());
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("access_token", PreferenceUtility.getAccessToken(mContext));

                conn.setRequestProperty("Expect", "100-continue");

                conn.setReadTimeout(connectionTimeOut);
                conn.setConnectTimeout(connectionTimeOut);

                conn.connect();

                Log.d("AccessToken", PreferenceUtility.getAccessToken(mContext));
                Log.d("Json", jsonObject.toString());
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("GET")) {
            // request method is GET

            StringBuilder sbParams = new StringBuilder();
            int i = 0;
            for (String key : params.keySet()) {
                try {
                    if (i != 0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=")
                            .append(URLEncoder.encode(params.get(key), charset));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {

                Log.d("AccessToken", PreferenceUtility.getAccessToken(mContext));

                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                conn.setRequestProperty("access_token", PreferenceUtility.getAccessToken(mContext));

                conn.setConnectTimeout(connectionTimeOut);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {

            Log.d("Response code", ""+ conn.getResponseCode());
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        if (result != null) {
            return result.toString();
        }
        return "";
    }

}
