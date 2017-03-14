package com.example.codyclawson.runescapecompanion;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DataQuery query;
    DataQuery query2;

    TextView accountText;

    String onlinePlayers;
    String totalAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountText = (TextView) findViewById(R.id.accountText);
        query = new DataQuery();
        query2 = new DataQuery();

        query.LoadNumOfOnline(new DataQuery.LoadCallbackString() {
            @Override
            public void GetString(String string) {
                onlinePlayers = ProcessString((string));
                setAccountText();
            }
        });

        query2.LoadNumOfAccounts(new DataQuery.LoadCallbackString() {
            @Override
            public void GetString(String string) {
                try {
                    JSONObject jsonData = new JSONObject(ProcessString(string));
                    totalAccounts = jsonData.getString("accounts");
                    setAccountText();
                } catch (JSONException e) {
                }
            }
        });
    }

    public void setAccountText(){
        accountText.setText(onlinePlayers + "/" + totalAccounts + " Online");
    }

    public void ParseJson(JSONObject json)
    {
        System.out.println("Parsing JSON:");

        System.out.println(json.toString());
    }

    public String ProcessString(String s){
        String result = "";
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(s);
        while(m.find()) {
            result += m.group(1);
        }
        return result;
    }

    public void goToGrandExchange(View view){
        System.out.print("Hello");
        Intent intent = new Intent(this, GrandExchangeActivity.class);
        startActivity(intent);
    }

    public void goToPlayer(View view){
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }
    public void goToGuide(View view){
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }

    public void goToWebpage(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.runelocus.com/tools/rs-item-id-list/?search=&order_by=itemlist_name"));
        startActivity(browserIntent);
    }



}
