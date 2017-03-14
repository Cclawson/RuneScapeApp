package com.example.codyclawson.runescapecompanion;

/**
 * Created by Kyle on 3/7/2017.
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

    public interface LoadCallbackString {
        void GetString(String string);
    }


    LoadCallback loadCallback;
    LoadCallbackString loadCallbackString;


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

    public void LoadItemData(String itemId, LoadCallback callback){
        loadCallback = callback;
        String item = "";

        try{
            item = URLEncoder.encode(itemId,"utf-8").toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        new DownloadRawData().execute("http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item="+ item);
    }

    public void LoadNumOfOnline(LoadCallbackString callback){
        loadCallbackString = callback;

        new DownloadRawString().execute("http://www.runescape.com/player_count.js?varname=iPlayerCount&callback=jQuery000000000000000_0000000000&_=0");
    }

    public void LoadNumOfAccounts(LoadCallbackString callback){
        loadCallbackString = callback;

        new DownloadRawString().execute("http://services.runescape.com/m=account-creation-reports/rsusertotal.ws?callback=jQuery000000000000000_0000000000&_=0");
    }

    public void LoadImage(String imageurl, LoadCallback callback){
        loadCallback = callback;
        String image = "";

        try{
            image = URLEncoder.encode(imageurl,"utf-8").toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        new DownloadRawData().execute(image);
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

    private class DownloadRawString extends AsyncTask<String, Void, String>
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
                rawData = new String(res);
                loadCallbackString.GetString(rawData);
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
