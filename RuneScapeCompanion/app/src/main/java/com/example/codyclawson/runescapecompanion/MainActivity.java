package com.example.codyclawson.runescapecompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    DataQuery query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = new DataQuery();

    }

    public void ParseJson(JSONObject json)
    {
        System.out.println("Parsing JSON:");

        System.out.println(json.toString());
    }

    public void OnClick(View view)
    {
        System.out.println("Clicked");
        query.LoadData(new DataQuery.LoadCallback() {
            @Override
            public void ProcessJson(JSONObject jsonObject) {
                ParseJson(jsonObject);
            }
        });
        //Need callback because async task?

    }





}
