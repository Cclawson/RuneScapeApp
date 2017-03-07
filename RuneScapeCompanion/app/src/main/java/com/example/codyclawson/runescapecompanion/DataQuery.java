package com.example.codyclawson.runescapecompanion;

/**
 * Created by Kyle on 3/7/2017.
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Kyle on 3/1/2017.
 */

public class DataQuery {

    JSONObject jsonData;
    public String rawData;

    public interface LoadCallback {
        void ProcessJson(JSONObject jsonObject);
    }


    LoadCallback loadCallback;


    public void LoadData(LoadCallback callback)
    {
        loadCallback = callback;
        new DownloadRawData().execute("https://apps.runescape.com/runemetrics/profile/profile?user=zekken%20asura&activities=20");


    }


    public void LoadUserData(String username,LoadCallback callback)
    {
        loadCallback = callback;
        String name = "";

        try{
            name = URLEncoder.encode(username,"utf-8").toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        new DownloadRawData().execute("https://apps.runescape.com/runemetrics/profile/profile?user=" + name + "&activities=20");

    }


    private class DownloadRawData extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            String link = params[0];

            try{
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                }

                return buffer.toString();
            }
            catch (UnsupportedEncodingException e)
            {
                //Do error display here
                e.printStackTrace();
            }
            catch (MalformedURLException e)
            {
                //Do error display here
                e.printStackTrace();

            }
            catch (java.io.IOException e)
            {
                //Do error display here
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String res)
        {
            try {
                ParseJson(res);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void ParseJson(String data) throws JSONException
    {
        //Bad data
        if(data == null) return;
        //System.out.println(data);
        //Create arraylist here
        jsonData = new JSONObject(data);
        loadCallback.ProcessJson(jsonData);
        rawData = new String(data);
    }














}
