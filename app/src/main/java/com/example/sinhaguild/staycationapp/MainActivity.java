package com.example.sinhaguild.staycationapp;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinhaguild.staycationapp.data.Config;
import com.example.sinhaguild.staycationapp.data.Price;
import com.example.sinhaguild.staycationapp.data.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button launch;
    public static final String TAG = "MainActivity";
    LocationActivity locationActivity;
    private String[] mWeatherTagList;

    //Venues Object
    private Venue[] mVenuesFood;
    private Venue[] mVenueShop;
//    TextView location;
//    String locationString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationActivity = new LocationActivity(getApplicationContext());
        locationActivity.getLocationConfiguration();
        locationActivity.getLocation();

        launch = (Button) findViewById(R.id.launch);
        //location = (TextView) findViewById(R.id.locationText);

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });

    }

}
