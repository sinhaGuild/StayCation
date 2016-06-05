package com.example.sinhaguild.staycationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
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
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anuragsinha on 16-06-01.
 */
public class LocationActivity extends LocationBaseActivity {

    private static final String TAG = "LocationBaseActivity";
    private ProgressDialog progressDialog;
    private TextView locationText;
    Context context;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = (TextView) findViewById(R.id.locationText);
        LocationManager.setLogType(LogType.GENERAL);
        getLocation();
    }

    public LocationActivity(Context context) {

        this.context = context;
        LocationManager.setLogType(LogType.GENERAL);
        getLocation();

        //getLocation();
    }

    public LocationActivity() {
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(true)
                .askForGooglePlayServices(true)
                .setMinAccuracy(200.0f)
                .setWaitPeriod(ProviderType.GOOGLE_PLAY_SERVICES, 5 * 1000)
                .setWaitPeriod(ProviderType.GPS, 10 * 1000)
                .setWaitPeriod(ProviderType.NETWORK, 5 * 1000)
                .setGPSMessage("Please turn on your GPS.")
                .setRationalMessage("Application requires your permission to continue.");
    }

    @Override
    public void onLocationChanged(Location location) {
        dismissProgress();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        Toast.makeText(LocationActivity.this, "From LocationActivity " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
        if (latitude != 0.0 && longitude != 0.0) {
            Intent intent = new Intent(LocationActivity.this, StaycationActivity.class);
            Bundle extras = new Bundle();
            extras.putDouble("Latitude", latitude);
            extras.putDouble("Longitude", longitude);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LocationActivity.this, "Location returned as 0.0", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationFailed(int failType) {
        dismissProgress();

        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                locationText.setText("Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                locationText.setText("Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                locationText.setText("Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.TIMEOUT: {
                locationText.setText("Couldn't get location, and timeout!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DENIED: {
                locationText.setText("Couldn't get location, because user didn't activate providers via settingsApi!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DIALOG: {
                locationText.setText("Couldn't display settingsApi dialog!");
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void setText(Location location) {
        String appendValue = location.getLatitude() + ", " + location.getLongitude() + "\n";
        String newValue;
        CharSequence current = locationText.getText();

        if (!TextUtils.isEmpty(current)) {
            newValue = current + appendValue;
        } else {
            newValue = appendValue;
        }

        locationText.setText(newValue);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationActivity{" +
                "locationText=" + getLongitude() + ", " + getLatitude() + '}';
    }

}



