package com.example.sinhaguild.staycationapp.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anuragsinha on 16-06-04.
 */
public class WeatherTag {

    public static final String TAG = "WeatherTag";
    private Context mContext;

    private String[] weatherTagList;

    private double latitude = 0.0;
    private double longitude = 0.0;

    public WeatherTag(Context context, double latitude, double longitude) {
        this.mContext = context;
        this.latitude = latitude;
        this.longitude = longitude;
        getWeatherTag(mContext, this.latitude, this.longitude);
        Log.i(TAG, "WeatherTag: " + "Latitude :" + latitude + " Longitude :" + longitude + "\n" + weatherTagList.toString());
    }


    /**
     * get Weather tag
     *
     * @return
     */
    private void getWeatherTag(Context mContext, double latitude, double longitude) {

        //http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=ccf7fc359139bd615824c08133f30938
        Uri.Builder builder = new Uri.Builder();
        builder.
                scheme("http").
                authority("api.openweathermap.org").
                appendPath("data").
                appendPath("2.5").
                appendPath("forecast").
                appendQueryParameter("lat", String.valueOf(latitude)).
                appendQueryParameter("lon", String.valueOf(longitude)).
                appendQueryParameter("appid", "ccf7fc359139bd615824c08133f30938");

        String url = builder.build().toString();
        Log.i(TAG, "getWeatherTag: " + url);

        JsonObjectRequest jsonObjectWeatherTag = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {

                            JSONArray jsonItemsArray = response.getJSONArray("list");
                            parseWeatherResponse(jsonItemsArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error.Response" + error.toString());
            }
        });
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        //Adding request to the queue
        requestQueue.add(jsonObjectWeatherTag);
    }

    public String[] getWeatherTagList() {
        return weatherTagList;
    }

    public void parseWeatherResponse(JSONArray jsonItemsArray) {
        if (jsonItemsArray != null) {
            weatherTagList = new String[jsonItemsArray.length()];
            try {
                for (int i = 0; i < jsonItemsArray.length(); i++) {
                    JSONObject jsonWeatherObject = jsonItemsArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                    String temp = jsonWeatherObject.getString("main") + ", " + jsonWeatherObject.getString("description");
                    weatherTagList[i] = temp;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "onResponse: Weather API returned empty response.");
        }
    }

    public void setWeatherTagList(String[] weatherTagList) {
        this.weatherTagList = weatherTagList;
    }
}
