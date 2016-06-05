package com.example.sinhaguild.staycationapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dexafree.materialList.view.MaterialListView;
import com.example.sinhaguild.staycationapp.data.Config;
import com.example.sinhaguild.staycationapp.data.Price;
import com.example.sinhaguild.staycationapp.data.Venue;
import com.mutualmobile.cardstack.CardStackLayout;
import com.mutualmobile.cardstack.utils.Units;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaycationActivity extends AppCompatActivity implements OnRestartRequest {

    public static final String TAG = "StaycationActivity";
    RequestQueue requestQueue;
    private Context mContext;
    private MaterialListView mListView;
    private double mLatitude;
    private double mLongitude;
    private Config mConfig = new Config();
    //Weather
    //private WeatherTag mWeatherTag;
    private String[] mWeatherTagList;
    //Venues Object
    private Venue[] mVenuesFood;
    private Venue[] mVenueShop;
    private CardStackLayout mCardStackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staycation2);
        // Save a reference to the context
        mContext = this;
         //Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        mCardStackLayout = (CardStackLayout) findViewById(R.id.cardStack);
        //get Bundle
        Bundle extras = getIntent().getExtras();
        setupWeatherCoordinates(extras);

        //Volley tasks here
        getFourSquareJSON();

    }

    public void getFourSquareJSON() {

        StringBuilder location = new StringBuilder();
        location.append(String.valueOf(mLatitude));
        location.append(",");
        location.append(String.valueOf(mLongitude));


        /**
         * Search mVenuesFood w/ Breakfast, Lunch, Dinner
         */
        final String url_food = Config.buildExploreURLForVenues(location.toString(), "food,drinks,coffee,trending", false, false);
        JsonObjectRequest jsonObjectRequestFood = new JsonObjectRequest
                (Request.Method.GET, url_food, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {

                            JSONArray jsonItemsArray = response.
                                    getJSONObject("response").
                                    getJSONArray("groups").
                                    getJSONObject(0).
                                    getJSONArray("items");
                            Log.d("Response", response.toString());
                            parseJsonItemsArray(jsonItemsArray, "food");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                });


        /**
         * Search mVenuesFood w/ Shops
         */
        final String url_shops = Config.buildExploreURLForVenues(location.toString(), "shops, arts, outdoors, sights, trending", false, false);
        JsonObjectRequest jsonObjectRequestShops = new JsonObjectRequest(Request.Method.GET, url_shops, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {

                            JSONArray jsonItemsArray = response.
                                    getJSONObject("response").
                                    getJSONArray("groups").
                                    getJSONObject(0).
                                    getJSONArray("items");

                            parseJsonItemsArray(jsonItemsArray, "shops");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        });

        /**
         * get Weather response
         * http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=ccf7fc359139bd615824c08133f30938
         */
        Uri.Builder builder = new Uri.Builder();
        builder.
                scheme("http").
                authority("api.openweathermap.org").
                appendPath("data").
                appendPath("2.5").
                appendPath("forecast").
                appendQueryParameter("lat", String.valueOf(mLatitude)).
                appendQueryParameter("lon", String.valueOf(mLongitude)).
                appendQueryParameter("appid", "ccf7fc359139bd615824c08133f30938");

        final String urlWeather = builder.build().toString();
        Log.i(TAG, "getWeatherTag: " + urlWeather);

        JsonObjectRequest jsonObjectWeatherTag = new JsonObjectRequest
                (Request.Method.GET, urlWeather, null, new Response.Listener<JSONObject>() {
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
        //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //Adding request to the queue
        requestQueue.add(jsonObjectRequestFood);
        requestQueue.add(jsonObjectRequestShops);
        requestQueue.add(jsonObjectWeatherTag);

        mCardStackLayout.setShowInitAnimation(Prefs.isShowInitAnimationEnabled());
        mCardStackLayout.setParallaxEnabled(Prefs.isParallaxEnabled());
        mCardStackLayout.setParallaxScale(Prefs.getParallaxScale(this));
        mCardStackLayout.setCardGap(Units.dpToPx(this, Prefs.getCardGap(this)));
        mCardStackLayout.setCardGapBottom(Units.dpToPx(this, Prefs.getCardGapBottom(this)));
        mCardStackLayout.setAdapter(new StaycationAdapter(this, mWeatherTagList, mVenuesFood, mVenueShop));

    }


    @Override
    public void onBackPressed() {
        if (mCardStackLayout.isCardSelected()) {
            mCardStackLayout.restoreCards();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void requestRestart() {
        Log.i(TAG, "Restarting MainActivity..");
        mCardStackLayout.removeAdapter();

        mCardStackLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFourSquareJSON();
            }
        }, 200);
    }


    /**
     * Volley Requests
     */



    private void parseJsonItemsArray(JSONArray jsonItemsArray, String venueSection) {
        JSONObject jsonItem;
        JSONObject jsonVenue;
        JSONArray jsonTips;
        JSONObject jsonPhotos;
        Venue tempVenue = new Venue();

        if (jsonItemsArray != null) {
            mVenuesFood = new Venue[jsonItemsArray.length()];
            mVenueShop = new Venue[jsonItemsArray.length()];


            for (int i = 0; i < jsonItemsArray.length(); i++) {
                try {
                    jsonItem = jsonItemsArray.getJSONObject(i);
                    jsonVenue = jsonItem.getJSONObject("venue");
                    jsonTips = jsonItem.getJSONArray("tips");

                    tempVenue = new Venue();

                    //Venue ID
                    tempVenue.setId(jsonVenue.getString("id"));

                    //Venue name
                    tempVenue.setName(jsonVenue.getString("name"));

                    //Location
                    tempVenue.setLocation_formatted_address(jsonVenue.getJSONObject("location").getString("formattedAddress"));

                    //Category name
                    tempVenue.setCategory_name(jsonVenue.getJSONArray("categories").getJSONObject(0).getString("name"));

                    //Price
                    Price price = new Price();
                    price.setCurrency(jsonVenue.getJSONObject("price").getString("currency"));
                    price.setMessage(jsonVenue.getJSONObject("price").getString("message"));
                    tempVenue.setPrice(price);

                    //Photos
                    if (jsonVenue.getJSONObject("photos") != null) {
                        jsonPhotos = jsonVenue.
                                getJSONObject("photos").
                                getJSONArray("groups").
                                getJSONObject(0).
                                getJSONArray("items").
                                getJSONObject(0);
                        tempVenue.setPhoto_prefix(jsonPhotos.getString("prefix"));
                        tempVenue.setPhoto_suffix(jsonPhotos.getString("suffix"));

                    } else {
                        Log.i(TAG, "parseJsonItemsArray: Photos is empty");
                    }

                    //Tips
                    if (jsonTips != null) {
                        String tips = jsonTips.getJSONObject(0).getString("text");
                        tempVenue.setTips(tips);
                    } else {
                        Log.i(TAG, "parseJsonItemsArray: Tips is empty");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Populate appropriate venue list
                switch (venueSection) {
                    case "shops":
                        mVenueShop[i] = tempVenue;
                        break;
                    case "food":
                        mVenuesFood[i] = tempVenue;
                        break;
                }

            }
        } else {
            Log.i(TAG, "parseJsonItemsArray: Items Array is empty");
        }

    }

    /**
     * setup latitude, longitude and weather Tag
     *
     * @param extras
     */
    private void setupWeatherCoordinates(Bundle extras) {
        if (extras != null) {
            mLatitude = extras.getDouble("Latitude");
            mLongitude = extras.getDouble("Longitude");
//            getWeatherTag(mLatitude, mLongitude);
//            //mWeatherTag = new WeatherTag(this,mLatitude, mLongitude);
//            if (mWeatherTagList != null){
//                Log.i(TAG, "onCreate: Weather API Sucess");
//            }else {
//                Log.i(TAG, "onCreate: Weather API failed");
//            }
        }
    }
//
//    private void getWeatherTag(double latitude, double longitude) {
//
//        //http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=ccf7fc359139bd615824c08133f30938
//        Uri.Builder builder = new Uri.Builder();
//        builder.
//                scheme("http").
//                authority("api.openweathermap.org").
//                appendPath("data").
//                appendPath("2.5").
//                appendPath("forecast").
//                appendQueryParameter("lat", String.valueOf(latitude)).
//                appendQueryParameter("lon", String.valueOf(longitude)).
//                appendQueryParameter("appid", "ccf7fc359139bd615824c08133f30938");
//
//        String url = builder.build().toString();
//        Log.i(TAG, "getWeatherTag: " + url);
//
//        JsonObjectRequest jsonObjectWeatherTag = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                        try {
//
//                            JSONArray jsonItemsArray = response.getJSONArray("list");
//                            parseWeatherResponse(jsonItemsArray);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, "Error.Response" + error.toString());
//            }
//        });
//        //Creating request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        //Adding request to the queue
//        requestQueue.add(jsonObjectWeatherTag);
//    }


    private void parseWeatherResponse(JSONArray jsonItemsArray) {
        if (jsonItemsArray != null) {
            mWeatherTagList = new String[jsonItemsArray.length()];
            try {
                for (int i = 0; i < jsonItemsArray.length(); i++) {
                    JSONObject jsonWeatherObject = jsonItemsArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                    String temp = jsonWeatherObject.getString("main") + ", " + jsonWeatherObject.getString("description");
                    mWeatherTagList[i] = temp;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "onResponse: Weather API returned empty response.");
        }
    }


}
