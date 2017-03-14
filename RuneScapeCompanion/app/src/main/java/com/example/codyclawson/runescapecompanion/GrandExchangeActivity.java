package com.example.codyclawson.runescapecompanion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GrandExchangeActivity extends AppCompatActivity {

    DataQuery query;

    EditText searchText;
    ImageView imageView;
    TextView nameText;
    TextView descText;
    TextView priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_exchange);

        query = new DataQuery();
        searchText = (EditText) findViewById(R.id.searchText);
        imageView = (ImageView) findViewById(R.id.itemImg);
        nameText = (TextView) findViewById(R.id.nameText);
        descText = (TextView) findViewById(R.id.descriptionText);
        priceText = (TextView) findViewById(R.id.currentPrice);

        nameText.setVisibility(View.INVISIBLE);
        descText.setVisibility(View.INVISIBLE);
        priceText.setVisibility(View.INVISIBLE);

    }

    public void SearchItems(View view) {
        query.LoadItemData(searchText.getText().toString(), new DataQuery.LoadCallback() {
                    @Override
                    public void ProcessJson(JSONObject jsonObject) {
                        if(jsonObject == null){
                            nameText.setText("Item Not Found");
                            imageView.setImageBitmap(null);
                        }else {
                            JSONObject item = null;
                            JSONObject priceInfo = null;
                            try {
                                item = new JSONObject((jsonObject.getString("item")));
                                priceInfo = new JSONObject(item.getString("current"));
                                nameText.setText(item.getString("name"));
                                descText.setText(item.getString("description"));
                                priceText.setText("Current Price: " + priceInfo.getString("price"));
                                nameText.setVisibility(View.VISIBLE);
                                descText.setVisibility(View.VISIBLE);
                                priceText.setVisibility(View.VISIBLE);
                                String urlstring = item.getString("icon_large").replace("\\", "");
                                new DownloadImageTask(imageView).execute(urlstring);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );
    }

    public void ParseJson(JSONObject json) {
        System.out.println("Parsing JSON:");

        System.out.println(json.toString());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        public DownloadImageTask(ImageView bmImage) {
            imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}

