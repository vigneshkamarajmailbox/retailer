/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.service;

import android.content.Context;
import android.util.Log;

import com.botree.retailerssfa.util.SFASharedPref;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServices {

    private static final String TAG = WebServices.class.getSimpleName();

    private WebServices() {

    }

    public static String callSettingWS(Context context, String json, String strUrl) {
        String responeCode = "";
        try {

            SFASharedPref sharedPref = SFASharedPref.getOurInstance();
            String accessToken = sharedPref.readString(SFASharedPref.PREF_AUTH_TOKEN);
            String loginCode = sharedPref.readString(SFASharedPref.PREF_LOGIN_CODE);

            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            conn.setRequestProperty("X-Auth-Token", accessToken);
            conn.setRequestProperty("X-Login-Code", loginCode);
            conn.setConnectTimeout(12000);
            // POST Data
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d(TAG, "callSettingWS: josn :" + json);
            wr.write(json);
            wr.flush();
            int fileLength = conn.getContentLength();
            Log.e("fileLength", String.valueOf(fileLength));

            responeCode = "" + conn.getResponseCode();
            // Read Server Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line);
            }
            Log.e("response", sb.toString());
            return sb.toString();
        } catch (IOException e) {
            Log.e("WSClient", "Error: " + e.getMessage(), e);

        }

        if (responeCode.equalsIgnoreCase("401") ||
                responeCode.equalsIgnoreCase("410") ||
                responeCode.equalsIgnoreCase("500")) {
            return responeCode;
        }
        return "";
    }

    public static boolean isValidResponse(String strResponse) {

        return strResponse != null && !strResponse.isEmpty();
    }


}