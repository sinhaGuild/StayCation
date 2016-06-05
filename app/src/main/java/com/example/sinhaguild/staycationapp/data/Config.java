package com.example.sinhaguild.staycationapp.data;

import android.net.Uri;
import android.util.Log;

/**
 * Created by anuragsinha on 16-06-02.
 */
public class Config {

    public static final String TAG = "Config";

    public Config() {
    }

    //Keys
    public static final String CLIENT_ID_TAG = "client_id";
    public static final String CLIENT_SECRET_TAG = "client_secret";
    public static final String CLIENT_ID = "QXPTCGM3TYZX3NIWD3HOZHSTT4B132FOIFE3IX2LXDZHFSUF";
    public static final String CLIENT_SECRET = "SUYRZPTMO5BKNUUWYJ5PK00DY04DAHSLKJMMTZNARLAAZVLW";

    public static final String VENUE_CORE = "api.foursquare.com";// v2/venues/
    //"https://api.foursquare.com/v2/venues/VENUE_ID/events"
    public static final String VENUE_EVENTS = "events";
    public static final String VENUE_EXPLORE = "explore";
    public static final String VENUE_MENU = "menu";
    public static final String VENUE_SEARCH = "search";
    public static final String VENUE_CATEGORIES = "categories";
    public static final String VENUE_TRENDING = "trending";
    public static final String VENUE_PHOTOS = "photos";


    /**
     * Build URL for Venue Events with attributes
     *
     * @param location
     * @param section  could be food, drinks, coffee, shops, arts, outdoors, sights, trending or specials, nextVenues, or topPicks
     * @param specials
     * @return
     */

    public static String buildExploreURLForVenues(String location, String section, boolean specials, boolean openNow) {


        String specialsTag;
        if (specials) {
            specialsTag = "1";
        } else {
            specialsTag = "0";
        }

        String isOpen;
        if (openNow) {
            isOpen = "1";
        } else {
            isOpen = "0";
        }

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").
                authority(VENUE_CORE).
                appendPath("v2").
                appendPath("venues").
                appendPath(VENUE_EXPLORE).
                appendQueryParameter(CLIENT_ID_TAG, CLIENT_ID).
                appendQueryParameter(CLIENT_SECRET_TAG, CLIENT_SECRET).
                appendQueryParameter("ll", location).
                appendQueryParameter("radius", "250").
                appendQueryParameter("section", section).
                appendQueryParameter("limit", "10").
                appendQueryParameter("venuePhotos", "1").
                appendQueryParameter("price", "1,2,3,4").
                appendQueryParameter("specials", specialsTag).
                appendQueryParameter("openNow", isOpen).
        appendQueryParameter("m","foursquare").appendQueryParameter("v","20160601");

        Log.v(TAG, "Explore URL : " + builder.build().toString());
        return builder.build().toString();
    }


    public static String buildEventsURLByVenueID(String venueID, String location, String section, boolean specials) {

        String specialsTag;
        if (specials) {
            specialsTag = "1";
        } else {
            specialsTag = "0";
        }

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").
                authority(VENUE_CORE).
                appendPath("v2").
                appendPath("venues").
                appendPath(venueID).
                appendPath(VENUE_EVENTS).
                appendQueryParameter(CLIENT_ID_TAG, CLIENT_ID).
                appendQueryParameter(CLIENT_SECRET_TAG, CLIENT_SECRET).
                appendQueryParameter("ll", location).
                appendQueryParameter("radius", "250").
                appendQueryParameter("section", section).
                appendQueryParameter("limit", "10").
                appendQueryParameter("venuePhotos", "1").
                appendQueryParameter("price", "1,2,3,4").
                appendQueryParameter("specials", specialsTag);

        return builder.build().toString();
    }

}
