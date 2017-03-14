package com.example.codyclawson.runescapecompanion;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerActivity extends AppCompatActivity {


    DataQuery query;

    EditText searchText;
    ImageView imageView;
    TextView nameText;
    TextView xpText;
    TextView skillText;
    TextView onlineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        query = new DataQuery();
        searchText = (EditText) findViewById(R.id.searchText);
        imageView = (ImageView) findViewById(R.id.itemImg);
        nameText = (TextView) findViewById(R.id.nameText);
        xpText = (TextView) findViewById(R.id.xpText);
        skillText = (TextView) findViewById(R.id.skilltext);
        onlineText = (TextView) findViewById(R.id.onlineText);

        nameText.setVisibility(View.INVISIBLE);
        xpText.setVisibility(View.INVISIBLE);
        skillText.setVisibility(View.INVISIBLE);
        onlineText.setVisibility(View.INVISIBLE);

    }


    public void searchPlayer(View view)
    {
        query.LoadUserData(searchText.getText().toString(),new DataQuery.LoadCallback() {
            @Override
            public void ProcessJson(JSONObject jsonObject) {
                try {
                    nameText.setText(jsonObject.getString("name"));
                    xpText.setText("Total Xp: " + jsonObject.getString("totalxp"));
                    skillText.setText("Total Skill: " + jsonObject.getString("totalskill"));
                    onlineText.setText("Online: " + jsonObject.getString("loggedIn"));
                    nameText.setVisibility(View.VISIBLE);
                    xpText.setVisibility(View.VISIBLE);
                    skillText.setVisibility(View.VISIBLE);
                    onlineText.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ParseJson(jsonObject);
            }
        });
    }

    public void ParseJson(JSONObject json) {
        System.out.println("Parsing JSON:");

        System.out.println(json.toString());
    }

}
