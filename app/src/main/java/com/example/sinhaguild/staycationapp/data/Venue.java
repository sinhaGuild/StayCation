
package com.example.sinhaguild.staycationapp.data;

import java.util.HashMap;
import java.util.Map;

public class Venue {

    private String id;
    private String name;
    private String location_formatted_address;
    private String category_name;
    //    private List<Category> categories = new ArrayList<Category>();
    private boolean verified;
    private Stats stats;
    private Price price;
    private double rating;
    private String ratingColor;
    private int ratingSignals;
    private boolean allowMenuUrlEdit;
    private Hours hours;


    //    private Photos photos;
    private String photo_suffix;
    private String photo_prefix;
    private String photo_url;

    //tips
    private String tips;

    private VenuePage venuePage;
    private String storeId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getLocation_formatted_address() {
        return location_formatted_address;
    }

    public void setLocation_formatted_address(String location_formatted_address) {
        this.location_formatted_address = location_formatted_address;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isVerified() {
        return verified;
    }

    /**
     * @param verified The verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * @return The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return The price
     */
    public Price getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * @return The rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return The ratingColor
     */
    public String getRatingColor() {
        return ratingColor;
    }

    /**
     * @param ratingColor The ratingColor
     */
    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    /**
     * @return The ratingSignals
     */
    public int getRatingSignals() {
        return ratingSignals;
    }

    /**
     * @param ratingSignals The ratingSignals
     */
    public void setRatingSignals(int ratingSignals) {
        this.ratingSignals = ratingSignals;
    }

    /**
     * @return The allowMenuUrlEdit
     */
    public boolean isAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }

    /**
     * @param allowMenuUrlEdit The allowMenuUrlEdit
     */
    public void setAllowMenuUrlEdit(boolean allowMenuUrlEdit) {
        this.allowMenuUrlEdit = allowMenuUrlEdit;
    }

    /**
     * @return The hours
     */
    public Hours getHours() {
        return hours;
    }

    /**
     * @param hours The hours
     */
    public void setHours(Hours hours) {
        this.hours = hours;
    }

    /**
     * @return The venuePage
     */
    public VenuePage getVenuePage() {
        return venuePage;
    }

    /**
     * @param venuePage The venuePage
     */
    public void setVenuePage(VenuePage venuePage) {
        this.venuePage = venuePage;
    }

    /**
     * @return The storeId
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * @param storeId The storeId
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPhoto_prefix() {
        return photo_prefix;
    }

    public void setPhoto_prefix(String photo_prefix) {
        this.photo_prefix = photo_prefix;
    }

    public String getPhoto_suffix() {
        return photo_suffix;
    }

    public void setPhoto_suffix(String photo_suffix) {
        this.photo_suffix = photo_suffix;
    }

    public String getPhoto_url() {
        return photo_prefix.concat("300x500").concat(photo_suffix);
    }

    public void setPhoto_url(String photo_prefix, String photo_suffix) {
        String photoURL = photo_prefix.concat("300x500").concat(photo_suffix);
        this.photo_url = photoURL;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
