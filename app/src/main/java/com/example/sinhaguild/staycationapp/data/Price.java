
package com.example.sinhaguild.staycationapp.data;

import java.util.HashMap;
import java.util.Map;

public class Price {

    private int tier;
    private String message;
    private String currency;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The tier
     */
    public int getTier() {
        return tier;
    }

    /**
     * 
     * @param tier
     *     The tier
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
